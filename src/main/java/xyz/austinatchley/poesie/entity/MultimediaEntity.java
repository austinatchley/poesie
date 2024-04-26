package xyz.austinatchley.poesie.entity;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record MultimediaEntity(
        @NonNull String chatResponse,
        @NonNull List<String> imageResponse) {
}
