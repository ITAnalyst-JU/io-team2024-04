package core.levels;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelEnumTest {
    // Constructor and getter are only two methods, and they cannot be tested separately.

    @Test
    void test_Level_1(){
        LevelEnum level = LevelEnum.LEVEL_1;

        Assertions.assertThat(level.getLevelNumber()).isEqualTo(1);
    }

    @Test
    void test_Level_2(){
        LevelEnum level = LevelEnum.LEVEL_2;

        Assertions.assertThat(level.getLevelNumber()).isEqualTo(2);
    }
}