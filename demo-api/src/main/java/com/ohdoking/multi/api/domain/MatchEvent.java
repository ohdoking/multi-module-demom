package com.ohdoking.multi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEvent {
	private int minute;
	private String type;
	private String team;
	private String playerName;
}