package com.math.MathLearningWebsite.utils.passwordGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class RandomPasswordGenerator {
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[{]};:'\",<.>/?";
    private static final Random RANDOM = new Random();
    private static final int DIGITS_COUNT = 10;
    private static final int LETTERS_COUNT = 10;
    private static final int SPECIAL_CHARS_COUNT = 5;

    public String randomPassword() {
        String password = generateRandomDigits(DIGITS_COUNT) +
                generateRandomLetters(LETTERS_COUNT) +
                generateRandomSpecialChars(SPECIAL_CHARS_COUNT);

        char[] passwordChars = password.toCharArray();
        shuffleCharArray(passwordChars);
        return new String(passwordChars);
    }

    private String generateRandomDigits(int count) {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < count; i++) {
            digits.append(RANDOM.nextInt(10)); // 0-9
        }
        return digits.toString();
    }

    private String generateRandomLetters(int count) {
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int randomInt = RANDOM.nextInt(26 * 2); // 26 for uppercase + 26 for lowercase
            char randomChar = (char) (randomInt < 26 ? 'A' + randomInt : 'a' + randomInt - 26);
            letters.append(randomChar);
        }
        return letters.toString();
    }

    private String generateRandomSpecialChars(int count) {
        StringBuilder specialChars = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int randomIndex = RANDOM.nextInt(SPECIAL_CHARS.length());
            specialChars.append(SPECIAL_CHARS.charAt(randomIndex));
        }
        return specialChars.toString();
    }

    private void shuffleCharArray(char[] charArray) {
        List<Character> charList = toCharacterList(charArray);
        Collections.shuffle(charList);
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = charList.get(i);
        }
    }

    private List<Character> toCharacterList(char[] charArray) {
        Character[] charObjects = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            charObjects[i] = charArray[i];
        }
        return Arrays.asList(charObjects);
    }
}
