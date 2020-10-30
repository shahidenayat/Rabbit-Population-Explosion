package com.sparta.engineering72.Simulation;

import com.sparta.engineering72.Animal.Animal;
import com.sparta.engineering72.Animal.Rabbit.FemaleRabbit;
import com.sparta.engineering72.Animal.Rabbit.MaleRabbit;
import com.sparta.engineering72.Animal.Rabbit.RabbitFluffle;
import com.sparta.engineering72.Settings.Settings;
import com.sparta.engineering72.Utility.Randomizer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RabbitLifeCycle implements LifeCycle {

    static ArrayList<FemaleRabbit> femaleRabbits = RabbitFluffle.getFemaleRabbitList();
    static ArrayList<MaleRabbit> maleRabbits = RabbitFluffle.getMaleRabbitList();
    public static BigInteger pregnancies = BigInteger.valueOf(0);
    public static BigInteger naturalDeathCount = BigInteger.valueOf(0);

    @Override
    public void naturalDeath() {
        Iterator<MaleRabbit> maleRabbitIterator = maleRabbits.iterator();
        while (maleRabbitIterator.hasNext()) {
            MaleRabbit rabbit = maleRabbitIterator.next();
            if (rabbit.isReadyToDie()) {
                naturalDeathCount.add(BigInteger.valueOf(rabbit.getCount()));
                maleRabbitIterator.remove();
            }
        }

        Iterator<FemaleRabbit> femaleRabbitIterator = femaleRabbits.iterator();

        while(femaleRabbitIterator.hasNext()) {
            FemaleRabbit rabbit = femaleRabbitIterator.next();
            if (rabbit.isReadyToDie()){
                naturalDeathCount.add(BigInteger.valueOf(rabbit.getCount()));
                femaleRabbitIterator.remove();
            }
        }
    }

    @Override
    public void breed() {
        if (pregnancies.compareTo(BigInteger.valueOf(0)) > 0) {
            List<Animal> animals = FemaleRabbit.breed(BigInteger.valueOf(pregnancies));
            for (Animal animal : animals) {
                if (animal.getGender() == Animal.Gender.MALE) {
                    maleRabbits.add((MaleRabbit) animal);
                } else {
                    femaleRabbits.add((FemaleRabbit) animal);
                }
            }
            pregnancies = BigInteger.valueOf(0);
        }
        getPregnancies();
    }
    private void getPregnancies() {
        BigInteger maleRabbitCount = BigInteger.valueOf(0);
        for (MaleRabbit rabbit : maleRabbits) {
            if (rabbit.isMature()) {
                maleRabbitCount.add(BigInteger.valueOf(rabbit.getCount());
            }
        }
        BigInteger femaleRabbitCount = BigInteger.valueOf(0);
        for (FemaleRabbit rabbit : femaleRabbits) {
            if (rabbit.isMature()) {
                femaleRabbitCount.add(BigInteger.valueOf(rabbit.getCount());
            }
        }
        BigInteger potentialPregnancies = maleRabbitCount.min(femaleRabbitCount);

        BigInteger totalPregnancies = BigInteger.valueOf(0);
        if (FemaleRabbit.getPregnancyChance() == 1.0d) {
            totalPregnancies = potentialPregnancies;
        } else {
            if (potentialPregnancies.compareTo(Settings.MAX_COUNT_THRESHOLD) > 0) {
                // see whats happening with this
                totalPregnancies = (BigInteger) (potentialPregnancies * FemaleRabbit.getPregnancyChance());
            } else {
                int potentialPregnanciesInt = potentialPregnancies.intValue();
                for (int i = 0; i < potentialPregnanciesInt; i++) {
                    if (Randomizer.getPregnancyChance(FemaleRabbit.getPregnancyChance()) == 1) {
                        totalPregnancies.add(BigInteger.valueOf(1));
                    }
                }
            }
        }
        pregnancies = totalPregnancies;
    }

    @Override
    public void age() {
        for (MaleRabbit rabbit: maleRabbits) {
            rabbit.incrementAge();
        }
        for (FemaleRabbit rabbit: femaleRabbits) {
            rabbit.incrementAge();
        }
    }
}