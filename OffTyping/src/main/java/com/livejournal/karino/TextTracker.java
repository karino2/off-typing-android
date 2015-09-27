package com.livejournal.karino;

public class TextTracker {
    public enum TypeResult {
        DOING_WELL,
        MISSTYPING,
        PENDING,
        DONE
    }

    private int misstypingCount;
    private String lastTyped = "";
    private String lastCorrect = "";
    private final String expected;

    public TextTracker(String expected) {
        this.expected = expected;
    }

    public TypeResult isWellTyped(String coming) {
        if (coming.equals(expected)) {
            return TypeResult.DONE;
        }

        if (0 != expected.indexOf(coming)) {
            if (coming.length() - lastCorrect.length() <= 1) {
                // Wrong text is given, but it might be just a transitional state.
                return TypeResult.PENDING;
            }

            return TypeResult.MISSTYPING;
        }

        return TypeResult.DOING_WELL;
    }

    public TypeResult reportTypedText(String typed) {
        if (isCorrecting(typed)) {
            misstypingCount++;
        }

        lastTyped = typed;

        TypeResult result = isWellTyped(typed);
        switch (result) {
            case DOING_WELL:
            case DONE:
                lastCorrect = typed;
                break;
        }

        return result;
    }

    private boolean isCorrecting(String typed) {
        boolean wasWrong = 0 != expected.indexOf(lastTyped);
        boolean isDeleting = typed.length() < lastTyped.length();
        return isDeleting && wasWrong;
    }

    public int getMisstypingCount() {
        return misstypingCount;
    }
}
