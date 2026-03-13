package com.jeperello.reactiveapi.dto;

public record MemoryMetricDto(long usedMemoryMB, long freeMemoryMB, long totalMemoryMB) {
}
