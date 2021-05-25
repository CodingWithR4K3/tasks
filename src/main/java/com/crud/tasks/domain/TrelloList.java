package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrelloList {
    private final String id;
    private final String name;
    private final boolean isClosed;
}
