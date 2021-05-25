package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrelloCard {
    private final String name;
    private final String description;
    private final String pos;
    private final String listId;
}
