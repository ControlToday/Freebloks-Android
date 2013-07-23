package de.saschahlusiak.freebloks.view.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import de.saschahlusiak.freebloks.R;

public class Theme {
	private String name;
	
	int drawable;
	float rgba[] = { 1, 1, 1, 1 };
	boolean isPreview, isDrawable;
	
	private Theme() {
		this.isDrawable = false;
		this.isPreview = false;
	}
	
	private Theme(int drawable) {
		this.isDrawable = true;
		this.isPreview = false;
		this.drawable = drawable;
	}
	
	private Theme(float r, float g, float b) {
		this.isDrawable = false;
		this.isPreview = false;
		setRGB(r, g, b);
	}

	String getName() {
		return name;
	}
	
	public final boolean isDrawable() {
		return isDrawable;
	}
	
	private void setDrawable(int drawable) {
		this.drawable = drawable;
		this.isDrawable = true;
	}
	
	BitmapDrawable getDrawable(Resources resources) {
		BitmapDrawable background = (BitmapDrawable) resources.getDrawable(drawable);

		background.setTileModeXY(TileMode.REPEAT, isPreview ? TileMode.MIRROR : TileMode.REPEAT);
		background.setFilterBitmap(true);
		return background;
	}
	
	final int getColor() {
		return Color.rgb((int)(rgba[0] * 255.0f), (int)(rgba[1] * 255.0f), (int)(rgba[2] * 255.0f));
	}
	
	public final float[] getRGBA() {
		return rgba;
	}
	
	void setRGB(float r, float g, float b) {
		this.rgba[0] = r;
		this.rgba[1] = g;
		this.rgba[2] = b;
	}
	
	public void apply(View view) {
		if (isDrawable)
			view.setBackgroundDrawable(getDrawable(view.getResources()));
		else
			view.setBackgroundColor(getColor());
	}

	public Bitmap getBitmap(Resources resources) {
		return BitmapFactory.decodeResource(resources, drawable);
	}
	
	public static Theme get(String theme, boolean preview) {
		Theme t = new Theme();
		
		if (theme.equals("black")) {
			t.setRGB(0, 0, 0);
		} else if (theme.equals("blue")) {
			t.setRGB(0.05f, 0.10f, 0.25f);
		} else if (theme.equals("texture_wood")) {
			t.setDrawable(R.drawable.texture_wood_fine);
			t.setRGB(0.45f, 0.45f, 0.45f);
		} else if (theme.equals("texture_metal")) {
			t.setDrawable(R.drawable.texture_metal);
			t.setRGB(0.55f, 0.55f, 0.55f);
		} else if (theme.equals("texture_bricks")) {
			t.setDrawable(R.drawable.texture_bricks);
			t.setRGB(0.50f, 0.55f, 0.55f);
		} else
			return null;
		
		t.isPreview = preview;
		t.name = theme;

		return t;
	}    	
}