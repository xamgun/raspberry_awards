package com.raspberryawards.raspberry_awards.dto;

import java.util.List;

public class WinnerResult {
    public WinnerResult() {
    }

    public WinnerResult(List<Winner> min, List<Winner> max) {
        this.min = min;
        this.max = max;
    }

    List<Winner> min;
    List<Winner> max;

    public List<Winner> getMin() {
        return min;
    }

    public void setMin(List<Winner> min) {
        this.min = min;
    }

    public List<Winner> getMax() {
        return max;
    }

    public void setMax(List<Winner> max) {
        this.max = max;
    }
}
