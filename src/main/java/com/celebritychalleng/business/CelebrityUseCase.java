package com.celebritychalleng.business;

import com.celebritychalleng.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class is responsible for the logic necessary for finding a celebrity on a team.
 */
public class CelebrityUseCase {

    public static final String THERE_ARE_NOT_CELEBRITY_IN_THIS_TEAM = "There is not celebrity in this team";
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String THE_CELEBRITY_IS = "The celebrity is: ";
    public static final int INDEX = 0;

    /**
     * This method implements the orchestration of other methods and returns the celebrity
     * if this person is found in the team, or else null if there is not a celebrity in the team
     *
     * @param team variable related to a group of people
     * @return the object of type celebrity if this is found
     */
    public Person findCelebrity(List<Person> team) {
        Person response = null;
        List<Person> peopleWithoutAcquaintances = filterPeopleWithoutAcquaintances(team);

        if (1 == peopleWithoutAcquaintances.size()) {
            response = getCelebrity(team.size(), findCandidateInsideTeamMate(team, peopleWithoutAcquaintances.get(INDEX)));
        }
        printLog(response);
        return response;
    }

    /**
     * This method filters the people without acquaintances
     *
     * @param team is the team requires filter by people without acquaintances
     * @return list of people without acquaintances
     */
    private List<Person> filterPeopleWithoutAcquaintances(List<Person> team) {
        return team.stream()
                .filter(teamMate -> teamMate.getKnows().size() == 0)
                .collect(Collectors.toList());
    }

    /**
     * This method gets the possibles celebrity candidates, search in a list team the candidate,
     * if it is known for all teammates excluding itself, then this is a candidate.
     *
     * @param team      is the team requires find the celebrity
     * @param candidate is the person candidate
     * @return Map with possibles candidates, if this Map have an only result then maybe he is a celebrity
     */
    private Map<String, List<Person>> findCandidateInsideTeamMate(List<Person> team, Person candidate) {
        return team.stream()
                .filter(teamMate -> teamMate != candidate)
                .map(teamMate -> teamMate.getKnows().contains(candidate) ? candidate : teamMate)
                .collect(Collectors.groupingBy(Person::getName));
    }

    /**
     * This method is responsible for getting the celebrity person if this person is known for the rest of teammate
     *
     * @param teamSize size of team
     * @param celebrities The map that contains the celebrity
     * @return celebrity Person, or null
     */
    private Person getCelebrity(int teamSize, Map<String, List<Person>> celebrities) {
        Map.Entry<String, List<Person>> entry = celebrities.entrySet().iterator().next();
        List<Person> people = entry.getValue();
        return people.size() == teamSize - 1 ? people.stream().findFirst().orElse(null) : null;
    }

    /**
     * This method is responsible for print logs
     *
     * @param response Person type object to print
     */
    private void printLog(Person response) {
        if (response == null) {
            LOGGER.log(Level.INFO, THERE_ARE_NOT_CELEBRITY_IN_THIS_TEAM);
        } else {
            LOGGER.log(Level.INFO, THE_CELEBRITY_IS.concat(Objects.requireNonNull(response).toString()));
        }
    }

}
