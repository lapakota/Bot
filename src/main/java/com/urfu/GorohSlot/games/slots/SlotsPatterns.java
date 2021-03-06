package com.urfu.GorohSlot.games.slots;

import com.urfu.GorohSlot.bot.User;
import org.glassfish.grizzly.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class SlotsPatterns {
    private final Slot[][] table;
    private int benefit;
    private final ArrayList<String> winSlotsArr;

    public ArrayList<String> getWinSlotsArr() {
        return winSlotsArr;
    }

    public int getBenefit() {
        return benefit;
    }

    public SlotsPatterns(Slot[][] table) {
        this.table = table;
        this.winSlotsArr = new ArrayList<>();
        this.benefit = 0;
    }

    public void CheckCollisionsAndAddMoney(User user){
        var patternsArr = new ArrayList<>(
                Arrays.asList(
                        patternLine1(),
                        patternLine2(),
                        patternLine3(),
                        patternLine4(),
                        patternV(),
                        patternReverseV(),
                        patternDiagonal1(),
                        patternDiagonalReverse1(),
                        patternDiagonal2(),
                        patternDiagonalReverse2(),
                        patternW(),
                        patternM()
                ));
        for (var pair : patternsArr)
        {
            if(pair.getFirst() != 0) {
                winSlotsArr.add(pair.getSecond().getCode());
                benefit += receiveMoney(pair, user);
            }
        }
    }

    private int receiveMoney(Pair<Integer, Slot> pair, User user){
        var currentBenefit = (int) (user.getBet() * pair.getFirst() * pair.getSecond().getMultiplier());
        user.AddMoney(currentBenefit);
        return currentBenefit;
    }

    private Pair<Integer, Slot> parsePattern(
            Slot num1,
            Slot num2,
            Slot num3,
            Slot num4,
            Slot num5) {
        var winSlot = num1.getCode();
        var slots = new ArrayList<String>();
        var count = 0;
        var slot = "";

        slots.add(num1.getCode());
        slots.add(num2.getCode());
        slots.add(num3.getCode());
        slots.add(num4.getCode());
        slots.add(num5.getCode());

        for (String s : slots) {
            slot = s;
            if (!slot.equals(winSlot)) {
                break;
            }
            count += 1;
        }
        // в конечном итоге множитель слота домножается на первое число в паре
        return switch (count) {
            case 3 -> new Pair<>(1, num1);
            case 4 -> new Pair<>(2, num1);
            case 5 -> new Pair<>(10, num1);
            default -> new Pair<>(0, num1);
        };
    }
    // ------
    private Pair<Integer, Slot> patternLine1(){
        return parsePattern(table[0][0], table[0][1], table[0][2], table[0][3], table[0][4]);
    }

    private Pair<Integer, Slot> patternLine2(){
        return parsePattern(table[1][0], table[1][1], table[1][2], table[1][3], table[1][4]);
    }

    private Pair<Integer, Slot> patternLine3(){
        return parsePattern(table[2][0], table[2][1], table[2][2], table[2][3], table[2][4]);
    }

    private Pair<Integer, Slot> patternLine4(){
        return parsePattern(table[3][0], table[3][1], table[3][2], table[3][3], table[3][4]);
    }
    // v
    private Pair<Integer, Slot> patternV(){
        return parsePattern(table[1][0], table[2][1], table[3][2], table[2][3], table[1][4]);
    }
    // ^
    private Pair<Integer, Slot> patternReverseV(){
        return parsePattern(table[2][0], table[1][1], table[0][2], table[1][3], table[2][4]);
    }
    // \_
    private Pair<Integer, Slot> patternDiagonal1(){
        return parsePattern(table[0][0], table[1][1], table[2][2], table[3][3], table[3][4]);
    }
    // /-
    private Pair<Integer, Slot> patternDiagonalReverse1(){
        return parsePattern(table[3][0], table[2][1], table[1][2], table[0][3], table[0][4]);
    }
    // -\
    private Pair<Integer, Slot> patternDiagonal2(){
        return parsePattern(table[0][0], table[0][1], table[1][2], table[2][3], table[3][4]);
    }
    // _/
    private Pair<Integer, Slot> patternDiagonalReverse2(){
        return parsePattern(table[3][0], table[3][1], table[2][2], table[1][3], table[0][4]);
    }

    // W
    private Pair<Integer, Slot> patternW(){
        return parsePattern(table[0][0], table[1][1], table[0][2], table[1][3], table[0][4]);
    }
    // M
    private Pair<Integer, Slot> patternM(){
        return parsePattern(table[3][0], table[2][1], table[3][2], table[2][3], table[3][4]);
    }
}
