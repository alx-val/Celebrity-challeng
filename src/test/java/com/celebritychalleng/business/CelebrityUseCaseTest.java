package com.celebritychalleng.business;

import com.celebritychalleng.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CelebrityUseCaseTest {

    public static final String ID_CELEBRITY_1 = "id-celebrity-1";
    public static final String CELEBRITY_1 = "Celebrity 1";
    public static final String ID_TEAMMATE_1 = "id-teammate-1";
    public static final String TEAMMATE_1 = "Teammate 1";
    public static final String ID_TEAMMATE_2 = "id-teammate-2";
    public static final String TEAMMATE_2 = "Teammate 2";
    private final CelebrityUseCase celebrityUseCase = new CelebrityUseCase();

    @Test
    public void teamWithCelebrity() {

        List<Person> celebrities = new ArrayList<>();
        List<Person> teamMates = new ArrayList<>();

        Person celebrity = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();
        celebrities.add(celebrity);

        Person teamMate1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        Person teamMate2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(celebrities)
                .build();

        teamMates.add(celebrity);
        teamMates.add(teamMate1);
        teamMates.add(teamMate2);

        Assertions.assertEquals(celebrity, celebrityUseCase.findCelebrity(teamMates));

    }


    @Test
    public void candidateCelebrityWithOneKnowTest() {

        List<Person> celebrities = new ArrayList<>();
        List<Person> teamMates = new ArrayList<>();

        Person celebrity = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(Collections.singletonList(Person.builder()
                        .identification(ID_TEAMMATE_1)
                        .name(TEAMMATE_1)
                        .knows(celebrities)
                        .build()))
                .build();
        celebrities.add(celebrity);

        Person teamMate1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        Person teamMate2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(celebrities)
                .build();

        teamMates.add(celebrity);
        teamMates.add(teamMate1);
        teamMates.add(teamMate2);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));

    }

    @Test
    public void nobodyKnowsNobodyTest() {

        List<Person> teamMates = new ArrayList<>();

        Person celebrity = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();

        Person teamMate1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(new ArrayList<>())
                .build();

        Person teamMate2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(new ArrayList<>())
                .build();

        teamMates.add(celebrity);
        teamMates.add(teamMate1);
        teamMates.add(teamMate2);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));

    }

    @Test
    public void onePersonNotKnowCandidateCelebrityTest() {

        List<Person> celebrities = new ArrayList<>();
        List<Person> teamMates = new ArrayList<>();

        Person celebrity = Person.builder()
                .identification(ID_CELEBRITY_1)
                .name(CELEBRITY_1)
                .knows(new ArrayList<>())
                .build();
        celebrities.add(celebrity);

        Person teamMate1 = Person.builder()
                .identification(ID_TEAMMATE_1)
                .name(TEAMMATE_1)
                .knows(celebrities)
                .build();

        Person teamMate2 = Person.builder()
                .identification(ID_TEAMMATE_2)
                .name(TEAMMATE_2)
                .knows(new ArrayList<>())
                .build();

        teamMates.add(celebrity);
        teamMates.add(teamMate1);
        teamMates.add(teamMate2);

        Assertions.assertNull(celebrityUseCase.findCelebrity(teamMates));
    }
}
