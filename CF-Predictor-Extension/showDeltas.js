var partyNum = 0;
var deltas = [];

function modifyPartyHtml(index, elem) {
	var delta = 0;

	if (partyNum > 0) {
		var handle = $(elem).find("td:eq(1)").find("a").first().html();
		//next 2 lines - fix for legendary grandmaster
		handle = handle.replace('<span class="legendary-user-first-letter">','');
		handle = handle.replace('</span>','');
		if (handle in deltas) {
			delta = Math.round(deltas[handle]);
		}
	}

	var text;
	if (partyNum == 0) {
		text = "<th class='top right' style='width: 4em;'><span title='Rating change''>&Delta;</span></th>";
	} else {
		var darkClass = "";
		if (partyNum % 2 == 1) {
			darkClass = "dark ";
		}
		if (delta > 0) {
			text = "<td class='" + darkClass + "right'><span style='color:green;font-weight:bold;'>+" + delta + "</span></td>";
		} else {
			text = "<td class='" + darkClass + "right'><span style='color:gray;font-weight:bold;'>" + delta + "</span></td>";
		}
	}

	partyNum++;
	$(elem).append(text);
}

function showDeltas() {
	var count = $(".standings").find("tr").length;
	if (count > 2) {
		var contestId = document.location.href.replace(/\D+/ig, ',').substr(1).split(',')[0];
		getDeltas(contestId, function() {
			$(".standings").find("tr").first().find("th").last().removeClass("right");
			$(".standings").find("tr").find("td").removeClass("right");
			$(".standings").find("tr").each(modifyPartyHtml);
			if (count % 2 == 0) {
				$(".standings").find("tr").last().find("td").last().replaceWith("<td class='smaller bottom dark right'> </td>");
			} else {
				$(".standings").find("tr").last().find("td").last().replaceWith("<td class='smaller bottom right'> </td>");
			}
		});
	}
}

function getDeltas(contestId, callback) {
	//var myServer = "http://localhost:8084/CF-PredictorFrontEnd/GetNextRatingServlet?contestId="
	var myServer = "http://cf-predictor.us-west-2.elasticbeanstalk.com/GetNextRatingServlet?contestId=";
	$.getJSON(myServer + contestId, function(data) {
		for (var i = 0; i < data.result.length; i++) {
			var handle = data.result[i].handle;
			var delta = data.result[i].newRating - data.result[i].oldRating;
			deltas[handle] = delta;
		}
		callback();
	});
}

showDeltas();
