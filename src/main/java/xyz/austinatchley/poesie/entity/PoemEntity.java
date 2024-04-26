package xyz.austinatchley.poesie.entity;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record PoemEntity(
        @NonNull String title,
        @NonNull String haiku,
        @NonNull String prose,
        @NonNull List<String> illustrationPrompts) {
}
