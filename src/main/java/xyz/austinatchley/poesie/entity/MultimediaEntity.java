package xyz.austinatchley.poesie.entity;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record MultimediaEntity(
        @NonNull String chatResponse,
        @NonNull String imageResponse) {
}
