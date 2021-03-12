package com.celebritychalleng.business;

import com.celebritychalleng.model.Person;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class is responsible for the logic necessary for finding a celebrity on a team.
 */
public class CelebrityUseCase {

    public static final String THERE_ARE_NOT_CELEBRITY_IN_THIS_TEAM = "There are not celebrity in this team";
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String THE_CELEBRITY_IS = "The celebrity is: ";

    /**
     * This method implements the orchestration of other methods and return the celebrity Person
     * if this is found in the team,  or else null if there are no celebrity in a team
     *
     * @param team is the team that necessity find the celebrity
     * @return the celebrity Person if this is found, or else null
     */
    public Person findCelebrity(List<Person> team) {

        Person response = null;
        List<Person> candidateCelebrity = getCandidate(team);

        if (1 < candidateCelebrity.size()) {
            LOGGER.log(Level.INFO, THERE_ARE_NOT_CELEBRITY_IN_THIS_TEAM);
        } else {
            Map<String, List<Person>> celebrity = getPossibleCelebrity(team, candidateCelebrity);
            if (1 == celebrity.size()) {
                response = getCelebrity(celebrity);
                LOGGER.log(Level.INFO, THE_CELEBRITY_IS.concat(response.toString()));
            } else {
                LOGGER.log(Level.INFO, THERE_ARE_NOT_CELEBRITY_IN_THIS_TEAM);
            }
        }

        return response;
    }

    /**
     * This method is responsible of get info about celebrity Person from Map
     * @param celebrity Map that contains the celebrity Person
     * @return celebrity Person
     */
    private Person getCelebrity(Map<String, List<Person>> celebrity) {
        Map.Entry<String, List<Person>> entry = celebrity.entrySet().iterator().next();
        List<Person> people = entry.getValue();
        return people.stream().findFirst().orElse(null);
    }

    /**
     * This method get the possibles candidates to celebrity, search in a list of team the candidate, if it is know for
     * all teamMate excluding itself then this is candidate.
     * @param team is the team that necessity find the celebrity
     * @param candidateCelebrity is the list with candidates
     * @return Map with possibles candidates, if this Map have only one result, then it is a possible celebrity
     */
    private Map<String, List<Person>> getPossibleCelebrity(List<Person> team, List<Person> candidateCelebrity) {
        return candidateCelebrity
                .stream()
                .flatMap(candidate -> team.stream()
                        .filter(teamMate -> teamMate != candidate)
                        .map(teamMate -> teamMate.getKnows().contains(candidate) ? candidate : teamMate))
                .collect(Collectors.groupingBy(Person::getName));
    }

    /**
     * This method obtains the candidates, it is possible if one person don't have knows
     * @param team is the team that necessity find the celebrity
     * @return list of candidates
     */
    private List<Person> getCandidate(List<Person> team) {
        return team.stream()
                .filter(teamMate -> teamMate.getKnows().size() == 0)
                .collect(Collectors.toList());
    }


}
