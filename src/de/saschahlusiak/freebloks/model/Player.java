package de.saschahlusiak.freebloks.model;

public class Player {
	public int m_stone_points_left;
	public int m_stone_count;
	int m_number_of_possible_turns;
	int m_position_points;

	int m_teammate;
	int m_nemesis;
	int m_number;

	Stone m_stone[] = new Stone[Stone.STONE_COUNT_ALL_SHAPES];
	
	public Player() {
		for (int i = 0; i < Stone.STONE_COUNT_ALL_SHAPES; i++){
			m_stone[i] = new Stone();
		}
	}
	
	void init(Spiel spiel, int playernumber){
		m_number = playernumber;
		for (int i = 0; i < Stone.STONE_COUNT_ALL_SHAPES; i++){
			m_stone[i].init(i);
		}
		refresh_data(spiel);
	}
	
	public void copyFrom(Player from) {
		this.m_stone_points_left = from.m_stone_points_left;
		this.m_stone_count = from.m_stone_count;
		this.m_number_of_possible_turns = from.m_number_of_possible_turns;
		this.m_position_points = from.m_position_points;
		this.m_teammate = from.m_teammate;
		this.m_nemesis = from.m_nemesis;
		this.m_number = from.m_number;
		for (int i = 0; i < m_stone.length; i++) {
			this.m_stone[i].copyFrom(from.m_stone[i]);
		}
	}
	
	public int getPlayerNumber() {
		return m_number;
	}
	
	public Stone get_stone(int n) {
		return m_stone[n];
	}
	
	void set_teammate(int player) {
		m_teammate = player;
	}
	
	void set_nemesis(int player) {
		m_nemesis = player;
	}
	
	 
	void refresh_data(Spiel spiel){
		m_stone_points_left = 0;
		m_number_of_possible_turns = 0;
		m_position_points = 0;
		m_stone_count = 0;

		for (int n = 0; n < Stone.STONE_COUNT_ALL_SHAPES; n++){
			Stone stone = m_stone[n];
			m_stone_count += stone.get_available();
			m_stone_points_left += stone.get_stone_points() * stone.get_available();
		}

		for (int x = 0; x < spiel.m_field_size_x; x++){
			for (int y = 0; y < spiel.m_field_size_y; y++){
				if (spiel.get_game_field(m_number, y, x) == Stone.FIELD_ALLOWED){
					for (int n = 0; n < Stone.STONE_COUNT_ALL_SHAPES; n++){
						Stone stone = m_stone[n];
						if (stone.get_available() > 0){
							int pos_turns;

							pos_turns = stone.calculate_possible_turns_in_position(spiel, m_number, y, x);
							m_number_of_possible_turns += pos_turns;
							m_position_points += pos_turns * stone.get_stone_position_points() * stone.get_stone_points();
						}
					}
				}
			}	
		}
	}
}
