package frc.robot.positions;

public enum LEDPattern {

    RAINBOW(-0.09),
    CONFETTI(-0.87),
    SINELON_OCEAN(-0-75),
    SINELON_LAVA(-0.73),
    TWINKLES(-0.53),
    COLOR_WAVE_RAINBOW(-0.45),
    COLOR_WAVE_PARTY(-0.43),
    LARSON_SCANNER(-0.35),
    CHASE_RED(-0.31),
    BLINK_RED(-0.11),
    BLINK_BLUE(-0.09),
    BLINK_YELLOW(-0.07),
    FIRE_MEDIUM(-0.59),
    FIRE_LARGE(-0.57),
    BLINK_WHITE(-0.05);

    private final double value;

    LEDPattern(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}