package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class TrelloBoard {
    private final String id;
    private final String name;
    private final List<TrelloList> lists;
}
