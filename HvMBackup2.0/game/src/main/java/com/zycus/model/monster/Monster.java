package com.zycus.model.monster;

import com.zycus.model.Creature;

public class Monster extends Creature {
	private String gene = "monster";
	private int infectionRating;

	public Monster(int life, int movementRange, String gene, int infectionRating) {
		super(life, movementRange);
		this.gene = gene;
		this.infectionRating = infectionRating;
	}

	public Monster() {
		super();
	}

	public Monster(int life, int movementRange) {
		super(life, movementRange);
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public int getInfectionRating() {
		return infectionRating;
	}

	public void setInfectionRating(int infectionRating) {
		this.infectionRating = infectionRating;
	}

	@Override
	public String toString() {
		return "Monster [gene=" + gene + ", infectionRating=" + infectionRating + "]";
	}
}