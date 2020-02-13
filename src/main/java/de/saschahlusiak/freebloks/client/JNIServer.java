package de.saschahlusiak.freebloks.client;

import android.util.Log;

import de.saschahlusiak.freebloks.model.GameMode;
import de.saschahlusiak.freebloks.model.GameState;
import de.saschahlusiak.freebloks.model.Shape;

public class JNIServer {
	final static String tag = JNIServer.class.getSimpleName();

	/* unfortunately Runtime.availableProcessors() returns only the number of online cores */
	public static native int get_number_of_processors();

	private static native int native_run_server(int game_mode, int field_size_x, int field_size_y, int[] stones, int ki_mode, int ki_threads);

	private static native int native_resume_server(
			int field_size_x,
			int field_size_y,
			int current_player,
			int spieler[],
			int field_data[],
			int player_stone_data[],
			int game_mode,
			int ki_mode,
			int ki_threads);


	public static int runServer(GameState spiel, GameMode gameMode, int field_size, int stones[], int ki_mode) {
		int ki_threads = get_number_of_processors();

		Log.d(tag, "spawning server with " + ki_threads + " threads");

		if (spiel == null)
			return native_run_server(gameMode.ordinal(), field_size, field_size, stones, ki_mode, ki_threads);
		else {
			int player_stones_available[] = new int[Shape.COUNT * 4];
			int i, j;

			for (i = 0; i < 4; i++)
				for (j = 0; j < Shape.COUNT; j++)
					player_stones_available[i * Shape.COUNT + j] = spiel.getPlayer(i).getStone(j).getAvailable();

			return native_resume_server(
					spiel.width,
					spiel.height,
					spiel.getCurrentPlayer(),
					spiel.getPlayerTypes(),
					spiel.getFields(),
					player_stones_available,
					gameMode.ordinal(),
					ki_mode,
					ki_threads);
		}
	}

	static {
		Log.d(tag, "loading server.so");
		System.loadLibrary("server");
	}
}