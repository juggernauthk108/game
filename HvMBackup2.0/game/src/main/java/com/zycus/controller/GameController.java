/*
 * game controller for v1.8
 * 
 * added:
 * endTurn mechanism
 * changed the end turn mechanism to (Human play -> Monster play -> both get gold) repeat
 * 
 * --added console ArrayList<String>
 * */


package com.zycus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zycus.model.Block;
import com.zycus.model.Board;
import com.zycus.model.Creature;
import com.zycus.model.human.Human;
import com.zycus.model.monster.Monster;

@Controller
public class GameController {

	@Autowired
	Board gameMat;

	HashMap<String, Block> mapOfHumans = new HashMap<String, Block>();
	HashMap<String, Block> mapOfMonsters = new HashMap<String, Block>();
	
	//will contain moves that should trigger endturn
	List<String> endTurnMovesList = new ArrayList<String>();
	
	//this will have the the list of Arraylist<string> list
	List<String> console = new ArrayList<String>();

	int flag = (new Random().nextInt() % 2 > 0) ? 1 : -1;

	int dieRoll() {
		return new Random().nextInt(101) + 1;
	}

	String chance() {
		flag *= -1;
		if (flag < 0)
			return "monster";
		return "human";
	}

	String getBlockUid(int row, int col) {
		return "" + row + "," + col;
	}
/***************************************************************************************************************************************************/
	//code for console ooutput simplification and eaier editing
	
	String gameStart()
	{
		return "game has been started";
	}
	
	
	
	String moved(String plays ,int prevRow, int prevCol, int row, int col, HttpSession session)
	{
		String prepend = "TURN " + (Integer)session.getAttribute("noOfTurns") + ": ";
		return prepend + plays+" moved from ["+ prevRow + ", " + prevCol +"] to [" + row + ", " + col + "]";
	}
	
	String attacked(String plays ,int prevRow, int prevCol, int row, int col, HttpSession session)
	{
		String prepend = "TURN: " + (Integer)session.getAttribute("noOfTurns") + " ";
		return prepend + plays+" attacked from ["+ prevRow + ", " + prevCol +"] to [" + row + ", " + col + "]";
	}
	
	String collected(String plays ,int row, int col, HttpSession session)
	{
		String prepend = "TURN: " + (Integer)session.getAttribute("noOfTurns") + " ";
		return prepend + plays+" collected money from ["+" row" + ", " + col + "]";
	}
	
	//code for console ooutput simplification and eaier editing ends
/***************************************************************************************************************************************************/

	void updateMap(boolean isHuman, int prevRow, int prevCol, int row, int col,
			Block newblock) {
		String oldBlockUid = getBlockUid(prevRow, prevCol);
		String newBlockUid = getBlockUid(row, col);

		if (isHuman) {
			mapOfHumans.remove(oldBlockUid);
			mapOfHumans.put(newBlockUid, newblock);
		} else {
			mapOfMonsters.remove(oldBlockUid);
			mapOfMonsters.put(newBlockUid, newblock);
		}
	}

	void deleteFromMap(boolean isHuman, int row, int col) {
		if (isHuman)
			mapOfHumans.remove(getBlockUid(row, col));
		else
			mapOfMonsters.remove(getBlockUid(row, col));
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String getIndex() {
		console.clear();
		return "/index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndex1() {
		console.clear();
		return "/index";
	}

	void endTurnMechanism(HttpSession session) {
		int initFlagState = (Integer) session.getAttribute("initFlagState");

		if (flag == initFlagState) {
			session.setAttribute("noOfTurns",
					(Integer) session.getAttribute("noOfTurns") + 1);
			grantPassiveMoney(session);
		}
	}

	void grantPassiveMoney(HttpSession session) {

		session.setAttribute("monsterMoney",
				(Integer) session.getAttribute("monsterMoney")
						+ (Integer) session.getAttribute("noOfTurns"));

		session.setAttribute("humanMoney",
				(Integer) session.getAttribute("humanMoney")
						+ (Integer) session.getAttribute("noOfTurns"));

	}

	// GAME START HERE
	@RequestMapping(value = "startGame", method = RequestMethod.GET)
	public String startGame(HttpSession session) {
		
		//adding the possible clicks that should result in an end turn n trigger endTurn mechanism at the end of makeMove()
		endTurnMovesList.add("attacking");
		endTurnMovesList.add("moving");
		endTurnMovesList.add("collect");
		
		ArrayList<ArrayList<Block>> gameMat = new ArrayList<ArrayList<Block>>();

		ArrayList<Block> listOfBlocks;

		Block block;

		for (int i = 0; i < 10; i++) {

			listOfBlocks = new ArrayList<Block>();

			for (int j = 0; j < 10; j++) {

				String blockUid = "" + i + "," + j;

				if (i == 0) {

					block = new Block(new Human(), i, j);

					mapOfHumans.put(blockUid, block);

				} else if (i == 9) {

					block = new Block(new Monster(), i, j);
					mapOfMonsters.put(blockUid, block);

				} else {

					block = new Block(null, i, j);

					block.setMoney(((new Random().nextInt() % 2) > 0) ? "yes"
							: null);

					if (block.getMoney() != null) {
						if (dieRoll() > 97) {
							block.setAmountMoney(dieRoll() + 100);
							System.out.println("Got Lucky at:" + "i: " + i
									+ " j: " + j);
						} else {
							block.setAmountMoney(dieRoll() / 2);
						}
					}

				}

				block.setUid(blockUid);

				listOfBlocks.add(block);
			}
			gameMat.add(listOfBlocks);
		}

		/*
		 * for (int i = humanMoneyLimit; i > 0; ) { for (ArrayList<Block>
		 * blockList : gameMat) { for (Block currBlock : blockList) { if ((new
		 * Random().nextInt() % 2 > 0) ? true : false) { humanMoneyLimit--;
		 * currBlock.setMoney("human");
		 * blockList.set(blockList.indexOf(currBlock), currBlock); } } } }
		 * 
		 * for (int i = monsterMoneyLimit; i > 0; ) { for (ArrayList<Block>
		 * blockList : gameMat) { for (Block currBlock : blockList) { if ((new
		 * Random().nextInt() % 2 > 0) ? true : false) { monsterMoneyLimit--;
		 * currBlock.setMoney("monster");
		 * blockList.set(blockList.indexOf(currBlock), currBlock); } } } }
		 */

		session.setAttribute("mode", "move");

		session.setAttribute("row", 0);
		session.setAttribute("col", 0);

		String ch = chance();

		session.setAttribute("opp", (ch.equals("human") ? "monster" : "human"));

		session.setAttribute("plays", ch);

		session.setAttribute("pastCreature", null);

		session.setAttribute("board", gameMat);

		session.setAttribute("monsterMoney", 0);

		session.setAttribute("humanMoney", 0);

		session.setAttribute("noOfTurns", 0);
		
		console = new ArrayList<String>();
		console.add(gameStart());
		session.setAttribute("console", console);
		// saving the state of the flag; for end turn mechanism
				session.setAttribute("initFlagState", flag);
		

		return "/startGame";
	}

	@RequestMapping(value = "setModeMove", method = RequestMethod.GET)
	public String setModeMove(HttpSession session) {
		session.setAttribute("mode", "move");
		return "/startGame";
	}

	@RequestMapping(value = "setModeAttack", method = RequestMethod.GET)
	public String setModeAttack(HttpSession session) {
		session.setAttribute("mode", "attack");
		return "/startGame";
	}

	@RequestMapping(value = "setModeCollect", method = RequestMethod.GET)
	public String setModeCollect(HttpSession session) {
		session.setAttribute("mode", "collect");
		return "/startGame";
	}

	@RequestMapping(value = "/ingame/{mode}/{row}/{col}", method = RequestMethod.GET)
	public String makeMove(@PathVariable("mode") String mode, @PathVariable("row") int row,
			@PathVariable("col") int col, HttpSession session) {
		
		
		int prevRow = (Integer)session.getAttribute("row");
		int prevCol = (Integer)session.getAttribute("col");

		ArrayList<ArrayList<Block>> gameMat = (ArrayList<ArrayList<Block>>) session.getAttribute("board");
		ArrayList<Block> listOfBlocks;

		if (mode.equals("attack")) {

			session.setAttribute("attackRange", gameMat.get(row).get(col).getCreature().getAttackRange());
			session.setAttribute("mode", "attacking");
			session.setAttribute("row", row);
			session.setAttribute("col", col);

		} else {
			if (mode.equals("move")) {
				session.setAttribute("mode", "moving");
				session.setAttribute("pastCreature", gameMat.get(row).get(col).getCreature().getMovementRange());
				session.setAttribute("row", row);
				session.setAttribute("col", col);
			} else {
				if (mode.equals("moving")) {

					/*
					 * Improve logic by avoiding creation of a temp block (will be looked upon at when going for v2)
					 */

					// save the block clicked in a temp block
					Block tempBlock = gameMat.get((Integer) session.getAttribute("row"))
							.get((Integer) session.getAttribute("col"));

					// replace the creature from temp block to the creature in
					// new block
					gameMat.get(row).get(col).setCreature(tempBlock.getCreature());

					// set the creature in temp block to null
					tempBlock.setCreature(null);

					// assign null to the gameMat at the correct location
					gameMat.get((Integer) session.getAttribute("row")).set((Integer) session.getAttribute("col"),
							tempBlock);

					boolean isHuman = false;
					if (session.getAttribute("plays").equals("human"))
						isHuman = true;

					// updating the mapOfThe{gene}
					updateMap(isHuman, prevRow, prevCol, row, col, gameMat.get(row).get(col));
					
					console.add(moved((String)session.getAttribute("plays"), prevRow, prevCol, row, col,session));
					session.setAttribute("console", console);

					session.setAttribute("board", gameMat);
					
					session.setAttribute("opp", session.getAttribute("plays"));
					session.setAttribute("plays", chance());
					
					//session.setAttribute("noOfTurns", (Integer) session.getAttribute("noOfTurns") + 1);
					endTurnMechanism(session);
					session.setAttribute("mode", "move");

				} else {
					if (mode.equals("attacking")) {

						Creature sourceCreature = gameMat.get((Integer) session.getAttribute("row"))
								.get((Integer) session.getAttribute("col")).getCreature();
						int attackpoints = sourceCreature.getAttackPoints();
						int accuracy = sourceCreature.getAccuracy();

						Creature opponent = gameMat.get(row).get(col).getCreature();
						int oppLife = opponent.getLife();
						int oppArmor = opponent.getArmor();

						if (dieRoll() < accuracy) {
							attackpoints -= (oppArmor * attackpoints) / 100;

							// minimum attack points
							if (attackpoints <= 0)
								attackpoints = 80;
							else {
								int delta = (new Random().nextInt(attackpoints / 4)) + 1;

								if (dieRoll() % 2 == 0)
									attackpoints += delta;
								else
									attackpoints -= delta;

								if (delta > attackpoints / 5) {
									System.out.println("CRITICAL HIT");
								}
							}

							// attack : reduce the life
							oppLife -= attackpoints;

							if (oppLife > 0) {
								gameMat.get(row).get(col).getCreature().setLife(oppLife);

								// reduce the armor
								gameMat.get(row).get(col).getCreature().setArmor(oppArmor - 1);
							}

							else {
								gameMat.get(row).get(col).setCreature(null);
								boolean isMonster = true;
								if (session.getAttribute("plays").equals("human"))
									isMonster = false;
								deleteFromMap(isMonster, row, col);
								
							}

						}

						else {
							System.out.println(sourceCreature.getGene() + " missed");
						}
						
						console.add(attacked((String)session.getAttribute("plays"), prevRow, prevCol, row, col,session));
						session.setAttribute("console", console);
						
						session.setAttribute("board", gameMat);
						session.setAttribute("opp", session.getAttribute("plays"));
						session.setAttribute("plays", chance());
						//session.setAttribute("noOfTurns", (Integer) session.getAttribute("noOfTurns") + 1);
						endTurnMechanism(session);
						session.setAttribute("mode", "move");
						
						
					
					}

					else {
						if (mode.equals("collect")) {
							//session.setAttribute("mode", "collecting");

							if (gameMat.get(row).get(col).getMoney() != null) {

								int gotMoneyFromBlock = gameMat.get(row).get(col).getAmountMoney();

								if (session.getAttribute("plays").equals("monster"))
									session.setAttribute("monsterMoney",
											(Integer) session.getAttribute("monsterMoney") + gotMoneyFromBlock);
								else
									session.setAttribute("humanMoney",
											(Integer) session.getAttribute("humanMoney") + gotMoneyFromBlock);
								
								
								console.add(collected((String)session.getAttribute("plays"), row, col,session));
								session.setAttribute("console", console);

								gameMat.get(row).get(col).setMoney(null);

								session.setAttribute("opp", session.getAttribute("plays"));
								session.setAttribute("plays", chance());
								
								//session.setAttribute("noOfTurns", (Integer) session.getAttribute("noOfTurns") + 1);
								endTurnMechanism(session);
								session.setAttribute("board", gameMat);
							} else {
								System.out.println("no resources to collect");
							}

							session.setAttribute("mode", "move");

						}

					}

				}
			}
		}
		
	
		
		/*//v1.8 endTurn Mechanism
		if(endTurnMovesList.contains((String)session.getAttribute("mode")))
				endTurnMechanism(session);*/
		
		//deprecated passiveMechanism: removed in v1.8
		
		/*This code generates passive money per turn
		if (session.getAttribute("plays").equals("human"))
			session.setAttribute("monsterMoney",
					(Integer) session.getAttribute("monsterMoney") + (Integer) session.getAttribute("noOfTurns"));
		else
			session.setAttribute("humanMoney",
					(Integer) session.getAttribute("humanMoney") + (Integer) session.getAttribute("noOfTurns"));*/

		
		//deprecated passiveMechanism: removed in v1.8 ends
		
		
		/*
		//this code is for logging the maps of humans and monsters after movement whatsoever

		int prevRow = (Integer) session.getAttribute("row");
		int prevCol = (Integer) session.getAttribute("col");
		
		System.out.println("Mode: "+session.getAttribute("mode")+" for "+session.getAttribute("plays"));
		if (session.getAttribute("plays").equals("human")) {
			System.out.println("HUMANS MAP for turn: " + session.getAttribute("noOfTurns"));
			System.out.println(mapOfHumans);
		} else {
			System.out.println("MONSTERS MAP" + session.getAttribute("noOfTurns"));
			System.out.println(mapOfMonsters);
		}
		
//		if(session.getAttribute("mode").equals("moving"))
//		{
			System.out.println("The piece was moved from "+prevRow+","+prevCol+" to "+row+", "+col);
			
			
			if (session.getAttribute("plays").equals("human"))
				System.out.println("and history block presence in map is: "+mapOfHumans.containsKey(getBlockUid(prevRow, prevCol)));
			else
				System.out.println("and history block presence in map is: "+mapOfMonsters.containsKey(getBlockUid(prevRow, prevCol)));
//		}
		
		System.out.println("\n\n\n\n\n");
		
		//logging ends
		*/
		
		System.out.println(console.get(console.size() - 1));
		
		return "/startGame";
	}

	// v1.8 modification below
	@RequestMapping(value = "/endTurn", method = RequestMethod.GET)
	String endTurn(HttpSession session) {
		
		session.setAttribute("opp", session.getAttribute("plays"));
		
		session.setAttribute("plays", chance());
		
		endTurnMechanism(session);
		
		return "/startGame";
	}
	// v1.8 modification ends

}
