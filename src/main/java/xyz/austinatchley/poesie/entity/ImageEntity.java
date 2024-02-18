package xyz.austinatchley.poesie.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ImageEntity(
        @NonNull String url,
        @NonNull String metadata) {
}
