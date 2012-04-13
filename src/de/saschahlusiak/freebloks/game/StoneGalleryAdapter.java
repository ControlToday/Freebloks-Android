package de.saschahlusiak.freebloks.game;

import de.saschahlusiak.freebloks.R;
import de.saschahlusiak.freebloks.model.Player;
import de.saschahlusiak.freebloks.model.Stone;
import de.saschahlusiak.freebloks.view.SimpleStoneView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class StoneGalleryAdapter extends BaseAdapter {
	Player player;
	Context context;
	
	StoneGalleryAdapter(Context context, Player player) {
		this.context = context;
		setPlayer(player);
	}
	
	void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public int getCount() {
		if (player == null)
			return 0;
		return Stone.STONE_COUNT_ALL_SHAPES;
	}

	@Override
	public Object getItem(int position) {
		return player.get_stone(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Stone stone = (Stone)getItem(position);
		View v = new SimpleStoneView(context, player.getPlayerNumber(), stone);
		return v;
	}

}
