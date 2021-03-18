package com.celebritychalleng.business;

import com.celebritychalleng.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CelebrityUseCaseTest {

    public static final String ID_CELEBRITY_1 = "id-celebrity-1";
    public static final String CELEBRITY_1 = "Celebrity 1";
    public static final String ID_TEAMMATE_1 = "id-teammate-1";
    public static final String TEAMMATE_1 = "Teammate 1";
    public static final String ID_TEAMMATE_2 = "id-teammate-2";
    public static final String TEAMMATE_2 = "Teammate 2";
    private final CelebrityUseCase celebrityUseCase = new CelebrityUseCase();
    private List<Person> celebrities;
    private List<Person> teamMates;

    @BeforeEach
    public void init() {
        celebrities = new ArrayList<>();
        teamMates = new ArrayList<>();
    }

    @Test
    void shouldReturnCelebrityWhenCelebrityInsideTeam() {

        Person celebrity = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();
        celebrities.add(celebrity);

        Person teamMateWithKnownCelebrity1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        Person teamMateWithKnownCelebrity2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(celebrities)
                .build();

        teamMates.add(celebrity);
        teamMates.add(teamMateWithKnownCelebrity1);
        teamMates.add(teamMateWithKnownCelebrity2);

        Assertions.assertEquals(celebrity, celebrityUseCase.findCelebrity(teamMates));
    }

    @Test
    void shouldNotReturnCelebrityWhenCelebrityKnowsTeam() {

        Person celebrityWithKnows = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(Collections.singletonList(Person.builder()
                        .identification(ID_TEAMMATE_1)
                        .name(TEAMMATE_1)
                        .knows(teamMates)
                        .build()))
                .build();
        celebrities.add(celebrityWithKnows);

        Person teamMateWithKnownCelebrity1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        Person teamMateWithKnownCelebrity2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(celebrities)
                .build();

        teamMates.add(teamMateWithKnownCelebrity1);
        teamMates.add(teamMateWithKnownCelebrity2);
        teamMates.add(celebrityWithKnows);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));
    }

    @Test
    public void shouldNotReturnCelebrityWhenNobodyKnowsNobody() {

        Person celebrityWithoutAcquaintances = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();

        Person teamMateWithoutAcquaintances1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(new ArrayList<>())
                .build();

        Person teamMateWithoutAcquaintances2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(new ArrayList<>())
                .build();

        teamMates.add(celebrityWithoutAcquaintances);
        teamMates.add(teamMateWithoutAcquaintances1);
        teamMates.add(teamMateWithoutAcquaintances2);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));

    }

    @Test
    public void shouldNotReturnCelebrityWhenOneTeammateNotKnowsNobody() {

        Person celebrity = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();
        celebrities.add(celebrity);

        Person teamMateWithKnownCelebrity = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        Person teamMateWithoutAcquaintances = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(new ArrayList<>())
                .build();

        teamMates.add(celebrity);
        teamMates.add(teamMateWithKnownCelebrity);
        teamMates.add(teamMateWithoutAcquaintances);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));
    }

    @Test
    public void shouldNotReturnCelebrityWhenOneTeammateKnowOnlyTeammate() {

        Person celebrityCandidate = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();
        celebrities.add(celebrityCandidate);

        Person teamMateWithKnownCelebrity = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        teamMates.add(teamMateWithKnownCelebrity);
        Person teamMateWithKnownTeamMate = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(teamMates)
                .build();
        teamMates.add(teamMateWithKnownTeamMate);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));

    }
}
