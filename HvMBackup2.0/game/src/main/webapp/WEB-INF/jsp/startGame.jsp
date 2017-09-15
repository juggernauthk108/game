<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="springx"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href='<c:url value="/styles/game.css"/>'>
<link rel="shortcut icon" href='<c:url value="/images/monster.ico"/>'>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>Startwars</title>
<style>
body {
	background-color: #000;
	background-size: cover;
}

.insideActionBox {
	margin: 0;
	padding: 0.3em;
}

.insideActionBox a {
	color: #b153d1;
}
</style>

<!-- Implementation of keypresses -->
<script>
	$(window).scroll(function() {
		sessionStorage.scrollTop = $(this).scrollTop();
	});

	$(document).ready(function() {
		if (sessionStorage.scrollTop != "undefined") {
			$(window).scrollTop(sessionStorage.scrollTop);
		}
	});

	function KeyPress(e) {
		var evtobj = window.event ? event : e
		if (evtobj.keyCode == 77) {
			$('#moveMode')[0].click();
		} else if (evtobj.keyCode == 65) {
			$('#attackMode')[0].click();
		} else if (evtobj.keyCode == 67) {
			$('#collectMode')[0].click();
		}
	};

	document.onkeydown = KeyPress;
</script>
</head>

<body>

	<!-- ---------------------------------------------------TOP BAR--------------------------------------------------- -->

	<div class="topBar">
		<span style="color: #bb0000">STRATWARS:&nbsp;</span> HUMANS VS
		MONSTERS
	</div>

	<!-- ----------------------------------------------------STAT BOX CONTENT-------------------------------------------------- -->

	<div class="statBox">

		<!-- Go Home or Quit Button -->
		<div id="homeBox">
			<a href="<c:url value="/index"/>">GO HOME&nbsp;(quit)</a>
		</div>

		<!-- Turn counter starts -->
		<div id="turnBox">
			<b>Turns:</b>&nbsp;${sessionScope.noOfTurns}</a>
		</div>

		<!-- Coin Counter starts -->
		<!-- Monster coins -->
		<div id="turnBox">
			<b>Monster's Coins:</b>&nbsp;${sessionScope.monsterMoney}</a>
		</div>
		<!-- Human coins -->
		<div id="turnBox">
			<b>Human's Coins:</b>&nbsp;${sessionScope.humanMoney}</a>
		</div>
		<!-- Coin counter ends -->

		<!-- Human Turn Indicator -->
		<c:if test="${sessionScope.plays == 'human'}">
			<div id="currentPlay">
				<span><b>Human's Play</b></span>
			</div>
			<div id="play">
				<span>Monster's Wait</span>
			</div>
		</c:if>
		<!-- Monster Turn Indicator -->
		<c:if test="${sessionScope.plays == 'monster'}">
			<div id="play">
				<span>Human's Wait</span>
			</div>
			<div id="currentPlay">
				<span><b>Monster's Play</b></span>
			</div>
		</c:if>
		<!-- Turn indicators ends -->
		<c:if
			test="${sessionScope.mode == 'move' || sessionScope.mode=='moving'}">
			<div id="modeStatBoxMove">
				<b>Mode:</b>
				<c:out value="${sessionScope.mode }"></c:out>
			</div>
		</c:if>
		<c:if
			test="${sessionScope.mode == 'collect' || sessionScope.mode=='collecting'}">
			<div id="modeStatBoxCollect">
				<b>Mode:</b>
				<c:out value="${sessionScope.mode }"></c:out>
			</div>
		</c:if>
		<c:if
			test="${sessionScope.mode == 'attack' || sessionScope.mode=='attacking'}">
			<div id="modeStatBoxAttack">
				<b>Mode:</b>
				<c:out value="${sessionScope.mode }"></c:out>
			</div>
		</c:if>
	</div>

	<!-- ----------------------------------------------INFORMATION STAT BOX-------------------------------------------------------- -->

	<div class="informationBox">
		<div class="insideActionBox">
			<b>Information (you might use):&nbsp;</b> You can press a, c, m on
			your keyboards to enter attack, collect, and move modes.
		</div>
		<div id="charStatsBox" class="insideActionBox">Info</div>
	</div>


	<!-- ----------------------------------------------ACTIONS BOX-------------------------------------------------------- -->

	<div class="actionBox">
		<div class="insideActionBox">
			<b>Change Mode:</b>
		</div>
		<div class="insideActionBox">
			<a id="moveMode" href='<c:url value="/setModeMove"/>'>Move</a>
		</div>
		<div class="insideActionBox">
			<a id="attackMode" href='<c:url value="/setModeAttack"/>'>Attack</a>
		</div>

		<div class="insideActionBox">
			<a id="collectMode" href='<c:url value="/setModeCollect"/>'>Collect</a>
		</div>

		<div class="insideActionBox">
			<a id="collectMode" href='<c:url value="#"/>'>Upgrade</a>
		</div>
		<!--version 1.8 changes  -->
		<div class="insideActionBox">
			<b>Other Actions:</b>
		</div>
		<div class="insideActionBox">
			<a href='<c:url value="/endTurn"/>'>End Turn</a>
		</div>
		<!--version 1.8 changes ends -->

	</div>



	<!-- ----------------------------------------------Test Area (delete when displayed properly)-------------------------------------------------------- -->



	<!-- ----------------------------------------------Test Area Ends-------------------------------------------------------- -->



	<!-- ---------------------------------------------THE GAME PLAY GRID--------------------------------------------------------- -->

	<div class="gameInsideBox">
		<table border="2" cellpadding="9"
			style="margin-left: auto; margin-right: auto">
			<c:forEach items="${sessionScope.board}" var="pList">
				<tr>
					<c:forEach items="${pList}" var="cList">

						<%-- it had: ${sessionScope.pastCreature} range --%>
						<td><c:if test="${not empty cList.creature }">
						 HP:${cList.creature.life} 
					</c:if> <!-- rendering money --> <c:if test="${not empty cList.money }">
							${cList.amountMoney}
							<img height="20px" width="20px"
									src='<c:url value="/images/money.png"/>' />
							</c:if> <!-----------------------------> <c:if
								test="${sessionScope.mode == 'move' }">


								<c:if test="${cList.creature.gene eq sessionScope.plays}">
									<a
										href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>
								</c:if>

								<c:if test="${not empty cList.creature.gene }">
									<img height="45px" width="32px"
										src='<c:url value="/images/${cList.creature.gene}.png"/>' />
								</c:if>

								<c:if test="${cList.creature.gene eq sessionScope.plays}">
									</a>

								</c:if>

							</c:if> <!-- rendering creatures even in attack mode --> <c:if
								test="${sessionScope.mode == 'attack' }">
								<c:if test="${cList.creature.gene eq sessionScope.plays}">
									<a
										href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>
								</c:if>

								<c:if test="${not empty cList.creature.gene }">
									<img height="45px" width="32px"
										src='<c:url value="/images/${cList.creature.gene}.png"/>' />
								</c:if>

								<c:if test="${cList.creature.gene eq sessionScope.plays}">
									</a>

								</c:if>

							</c:if> <!--  --> <!-- rendering creatures even in attacking mode --> <c:if
								test="${sessionScope.mode == 'attacking' }">
								<!--write attack logic here (PLEASE!)-->

								<!-- copied code  below -->

								<c:if
									test="${cList.creature.gene ne sessionScope.plays && not empty cList.creature.gene}">
									<c:if test="${sessionScope.row gt cList.x}">

										<c:if
											test="${(sessionScope.row - cList.x) lt sessionScope.attackRange + 1  && cList.y == sessionScope.col}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 50px; width: 50px;"
												src='<c:url value="/images/attack.gif"/>' />

											</a>

										</c:if>

									</c:if>

									<c:if test="${sessionScope.row lt cList.x}">

										<c:if
											test="${(cList.x - sessionScope.row) lt sessionScope.attackRange + 1  && cList.y == sessionScope.col}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 50px; width: 50px;"
												src='<c:url value="/images/attack.gif"/>' />
											</a>

										</c:if>

									</c:if>

									<!-- CODE FOR VERTICAL MOVEMENT -->
									<c:if test="${sessionScope.col lt cList.y}">

										<c:if
											test="${( cList.y - sessionScope.col ) lt sessionScope.attackRange + 1  && cList.x == sessionScope.row}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 50px; width: 50px;"
												src='<c:url value="/images/attack.gif"/>' />
											</a>

										</c:if>

									</c:if>

									<c:if test="${sessionScope.col gt cList.y}">

										<c:if
											test="${(  sessionScope.col - cList.y ) lt sessionScope.attackRange + 1  && cList.x == sessionScope.row}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 50px; width: 50px;"
												src='<c:url value="/images/attack.gif"/>' />
											</a>

										</c:if>

									</c:if>
								</c:if>
								<!-- copied code ends -->

								<!--c:if below gives freedom to player of changing the sprite on click  -->
								<c:if
									test="${cList.creature.gene == 'human' || cList.creature.gene == 'monster'}">


									<c:if test="${cList.creature.gene eq sessionScope.plays}">

										<a
											href='<c:url value="/ingame/attack/${cList.x}/${cList.y}" />'>
									</c:if>


									<img height="45px" width="32px"
										src='<c:url value="/images/${cList.creature.gene}.png"/>' />


									<c:if test="${cList.creature.gene eq sessionScope.plays}">
										</a>

									</c:if>
								</c:if>
								<!--c:if ends  -->

							</c:if> <!--  --> <!-- -------------------------------------------CODE FOR MOVEMENT---------------------------------------------------------- -->
							<c:if test="${sessionScope.mode == 'moving' }">





								<c:if test="${empty cList.creature.gene }">

									<%--For Debugging Purposes: COZ WE STUPID 
										Clicked: ${sessionScope.row},${sessionScope.col}<br/>
										Goto: ${cList.x},${cList.y}
										Diff: ${sessionScope.row - cList.x},${sessionScope.col - cList.y}
										 --%>

									<!-- CODE FOR HORIZONTAL MOVEMENT -->
									<c:if test="${sessionScope.row gt cList.x}">

										<c:if
											test="${(sessionScope.row - cList.x) lt sessionScope.pastCreature + 1  && cList.y == sessionScope.col}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 30px; width: 30px;"
												src='<c:url value="/images/here.gif"/>' />
											</a>

										</c:if>

									</c:if>

									<c:if test="${sessionScope.row lt cList.x}">

										<c:if
											test="${(cList.x - sessionScope.row) lt sessionScope.pastCreature + 1  && cList.y == sessionScope.col}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 30px; width: 30px;"
												src='<c:url value="/images/here.gif"/>' />
											</a>

										</c:if>

									</c:if>

									<!-- CODE FOR VERTICAL MOVEMENT -->
									<c:if test="${sessionScope.col lt cList.y}">

										<c:if
											test="${( cList.y - sessionScope.col ) lt sessionScope.pastCreature + 1  && cList.x == sessionScope.row}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 30px; width: 30px;"
												src='<c:url value="/images/here.gif"/>' />
											</a>

										</c:if>

									</c:if>

									<c:if test="${sessionScope.col gt cList.y}">

										<c:if
											test="${(  sessionScope.col - cList.y ) lt sessionScope.pastCreature + 1  && cList.x == sessionScope.row}">
											<a
												href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>

												<img style="height: 30px; width: 30px;"
												src='<c:url value="/images/here.gif"/>' />
											</a>

										</c:if>

									</c:if>

									<!-- CODE FOR DIAGONAL MOVEMENT -->

								</c:if>

								<!--c:if below gives freedom to player of changing the sprite on click  -->
								<c:if
									test="${cList.creature.gene == 'human' || cList.creature.gene == 'monster'}">


									<c:if test="${cList.creature.gene eq sessionScope.plays}">

										<a href='<c:url value="/ingame/move/${cList.x}/${cList.y}" />'>
									</c:if>


									<img height="45px" width="32px"
										src='<c:url value="/images/${cList.creature.gene}.png"/>' />


									<c:if test="${cList.creature.gene eq sessionScope.plays}">
										</a>

									</c:if>
								</c:if>
								<!--c:if ends  -->

							</c:if> <c:if test="${sessionScope.mode == 'collect' }">

								<c:if test="${cList.creature.gene eq sessionScope.plays}">
									<a
										href='<c:url value="/ingame/${sessionScope.mode}/${cList.x}/${cList.y}" />'>
								</c:if>

								<c:if
									test="${cList.creature.gene == 'human' || cList.creature.gene == 'monster'}">
									<img height="45px" width="32px"
										src='<c:url value="/images/${cList.creature.gene}.png"/>' />
								</c:if>

								<c:if test="${cList.creature.gene eq sessionScope.plays}">
									</a>
								</c:if>

							</c:if></td>

					</c:forEach>
					<!-- ------------------------------------------------------------------------------------------------------ -->
				</tr>

			</c:forEach>
			<!-- -------------------------------------------------CODE FOR CONSOLE----------------------------------------------------- -->

		</table>
	</div>

	<div class="consoleHeadBox">
		<!-- Console main bar -->
		<div id="consoleHeadBoxContent">
			<b>Logged in as:</b> --playerName--
		</div>
		<div id="consoleHeadBoxContent">
			<b>It is:</b> ${sessionScope.plays}
		</div>
	</div>

	<div class="consoleMainBox">
		<!-- Console output box -->
		Console output contains all the actions you have performed in this
		match. Refer to this console whenever you want to see actions log.
		<c:forEach var="i" items="${console}">
			<p>>>&nbsp;${i}</p>
		</c:forEach>
	</div>
</body>
</html>