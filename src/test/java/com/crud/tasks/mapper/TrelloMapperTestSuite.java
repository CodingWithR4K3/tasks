package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {

        //Given
        List<TrelloListDto> testListDto = new ArrayList<>();
        testListDto.add(new TrelloListDto("1", "TestList", false));
        List<TrelloBoardDto> testBoardDto = new ArrayList<>();
        testBoardDto.add(new TrelloBoardDto("1", "TestBoard", testListDto));
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(testBoardDto);
        //Then
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("TestBoard", trelloBoards.get(0).getName());
        assertEquals("TestList", trelloBoards.get(0).getLists().get(0).getName());
        assertEquals(1, trelloBoards.size());
    }


    @Test
    public void testMapToBoardsDto() {

        //Given
        List<TrelloList> testList = new ArrayList<>();
        testList.add(new TrelloList("1", "TestList", false));
        List<TrelloBoard> testBoard = new ArrayList<>();
        testBoard.add(new TrelloBoard("1", "TestBoard", testList));
        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(testBoard);
        //Then
        assertEquals("1", trelloBoardsDto.get(0).getId());
        assertEquals("TestBoard", trelloBoardsDto.get(0).getName());
        assertEquals("TestList", trelloBoardsDto.get(0).getLists().get(0).getName());
        assertEquals(1, trelloBoardsDto.size());
    }

    @Test
    public void testMapToList() {

        //Given
        List<TrelloListDto> testListDto = new ArrayList<>();
        testListDto.add(new TrelloListDto("1", "TestList1", false));
        testListDto.add(new TrelloListDto("2", "TestList2", false));
        testListDto.add(new TrelloListDto("3", "TestList3", false));
        //When
        List<TrelloList> testList = trelloMapper.mapToList(testListDto);
        //Then
        assertEquals("1", testList.get(0).getId());
        assertEquals("TestList3", testList.get(2).getName());
        assertEquals(3, testList.size());

    }

    @Test
    public void testMapToListDto() {

        //Given
        List<TrelloList> testList = new ArrayList<>();
        testList.add(new TrelloList("1", "TestList1", false));
        testList.add(new TrelloList("2", "TestList2", false));
        testList.add(new TrelloList("3", "TestList3", false));
        //When
        List<TrelloListDto> testListDto = trelloMapper.mapToListDto(testList);
        //Then
        assertEquals("1", testListDto.get(0).getId());
        assertEquals("TestList2", testListDto.get(1).getName());
        assertEquals(3, testListDto.size());
    }

    @Test
    public void testMapToCard() {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Description", "Top", "1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Test", trelloCard.getName());
        assertEquals("Description", trelloCard.getDescription());
        assertEquals("Top", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {

        //Given
        TrelloCard trelloCard = new TrelloCard("Test", "Description", "Top", "1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Test", trelloCardDto.getName());
        assertEquals("Description", trelloCardDto.getDescription());
        assertEquals("Top", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
    }
}