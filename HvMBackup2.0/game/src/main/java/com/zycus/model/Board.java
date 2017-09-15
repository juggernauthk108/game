package com.zycus.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Board {

	ArrayList<ArrayList<Block>> board;

	public Board(ArrayList<ArrayList<Block>> board) {
		super();
		this.board = board;
	}

	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ArrayList<Block>> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<ArrayList<Block>> board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Board [board=" + board + "]";
	}

}
