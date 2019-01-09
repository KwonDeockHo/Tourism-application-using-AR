/*
 * Copyright (C) 2010- Peer internet solutions
 * 
 * This file is part of mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.stampar;

import java.text.DecimalFormat;
import java.lang.String;

import org.stampar.lib.MixUtils;
import org.stampar.lib.gui.PaintScreen;
import org.stampar.lib.gui.TextObj;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Path;
import android.location.Location;

/**
 * This markers represent the points of interest.
 * On the screen they appear as circles, since this
 * class inherits the draw method of the Marker.
 * 
 * @author hannes
 * 
 */
public class POIMarker extends LocalMarker {

	public static final int MAX_OBJECTS = 20;
	public static final int OSM_URL_MAX_OBJECTS = 5;
	//public Context mixContext;

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	public MixState state;

	// gif 이미지의 재생을 위한 변수
	public long mvStart;

	public POIMarker(String id, String title, double latitude, double longitude,
			double altitude, String URL, int type, int color) {
		super(id, title, latitude, longitude, altitude, URL, type, color);
		//Log.v("GGGG", "How?? Long");

	}

	@Override
	public void update(Location curGPSFix) {
		super.update(curGPSFix);
	}

	@Override
	public int getMaxObjects() {
		return MAX_OBJECTS;
	}

	@Override
	public void drawCircle(PaintScreen dw, Bitmap[] bitArray, Movie[] mv, SharedPreferences pref) {

		// 여기서 리소스를 정하자..
		//Drawable d1 = ctx.getResources().getDrawable(R.drawable.test1101);
		//Bitmap target = null;
		/*
		if(target == null)
		{
			// 그냥 그림 자체에 과부하가 많이 걸리는 것이다..
			//Log.v("DONGS_DD", "그림을 생성하는가?");
			target = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.test1101);
		}
		*/
			//d1.setBounds(100,100,100,100);
			if (isVisible) {
				float maxHeight = dw.getHeight();
				dw.setStrokeWidth(maxHeight / 100f);
				// 기본 값 false
				dw.setFill(true);
				// 기본 값, getColor();
				dw.setColor(getColour());

				// draw circle with radius depending on distance
				// 0.44 is approx. vertical fov in radians
				double angle = 2.0 * Math.atan2(10, distance);
				double radius = Math.max(
						Math.min(angle / 0.44 * maxHeight, maxHeight),
						maxHeight / 25f);

			/*
			 * distance 100 is the threshold to convert from circle to another
			 * shape
			 */
				// 거리에 대한 조절은 여기서 해도 될 듯. 기본 값 100
/*
				if (distance < 100.0)
				{
					//Log.v("DDD", "일로 들어온다(가까움)");
					//otherShape(dw);
					dw.paintImg(bitArray[0], cMarker.x, cMarker.y, (float)radius);
				}
				*/
				if(distance < 1500.0)
				{

					float currentAngle = MixUtils.getAngle(cMarker.x, cMarker.y,signMarker.x, signMarker.y);
					if(dongs_ID.equals("100"))
					{
						dw.paintImg(bitArray[0], cMarker.x - 150, cMarker.y - 75, currentAngle);
					}
					else if(dongs_ID.equals("200"))
					{
						//bitArray[0].setWidth((int)radius);
						//bitArray[0].setHeight((int)radius);
						dw.paintImg(bitArray[1],  cMarker.x - 150, cMarker.y - 75, currentAngle);
						//dw.paintCircle(cMarker.x, cMarker.y, (float) radius);
					}
					else if(dongs_ID.equals("301"))
					{
						dw.paintGif(mv[1], cMarker.x - 200, cMarker.y - 150, (float)radius);
					}
					else if(dongs_ID.equals("302"))
					{
						dw.paintGif(mv[0], cMarker.x - 200, cMarker.y - 150, (float)radius);
					}
					else if(dongs_ID.equals("303"))
					{
						//dw.paintGif(mv[3], cMarker.x - 200, cMarker.y - 150, (float)radius);
					}
					else if(dongs_ID.equals("304"))
					{
						dw.paintGif(mv[2], cMarker.x - 200, cMarker.y - 150, (float)radius);
					}
					// 무조건 context 의 값을 가져와야 한다..
					//target = BitmapFactory.decodeResource(state.,	R.drawable.test1101);\
					//dw.paintCircle(cMarker.x, cMarker.y, (float) radius);
				}
				if(distance < 600.0)
				{
					if(dongs_ID.equals("301"))
					{
						if(pref.getInt("STAMP_1", 0) == 0)
						{
							SharedPreferences.Editor edit;
							edit = pref.edit();
							edit.putInt("STAMP_1", 1);
							edit.commit();
						}
						else
						{

						}
					}
					else if(dongs_ID.equals("302"))
					{
						if(pref.getInt("STAMP_2", 0) == 0)
						{
							SharedPreferences.Editor edit;
							edit = pref.edit();
							edit.putInt("STAMP_2", 1);
							edit.commit();
						}
						else
						{

						}
					}
					else if(dongs_ID.equals("303"))
					{
						if(pref.getInt("STAMP_3", 0) == 0)
						{
							SharedPreferences.Editor edit;
							edit = pref.edit();
							edit.putInt("STAMP_3", 1);
							edit.commit();
						}
						else
						{

						}
					}
					else if(dongs_ID.equals("304"))
					{
						if(pref.getInt("STAMP_4", 0) == 0)
						{
							SharedPreferences.Editor edit;
							edit = pref.edit();
							edit.putInt("STAMP_4", 1);
							edit.commit();
						}
						else
						{

						}
					}
						//SharedPreferences.Editor edit;
						//edit = pref.edit();
						//if(pref.getInt("STAMP_1"))
						//pref.edit().commit();


				}
			}


	}

	@Override
	public void drawTextBlock(PaintScreen dw) {
		float maxHeight = Math.round(dw.getHeight() / 10f) + 1;
		// TODO: change textblock only when distance changes
		String textStr = "";
		// 거리를 측정하는 부분.
		double d = distance;
		DecimalFormat df = new DecimalFormat("@#");
		if (d < 1000.0) {
			textStr = title + " " + df.format(d) + "m";
		} else {
			d = d / 1000.0;
			textStr = title + " " + df.format(d) + "km";
		}


		textBlock = new TextObj(textStr, Math.round(maxHeight / 2f) + 1, 250,
				dw, underline);



		if (isVisible) {
			// based on the distance set the colour
			/*
			if (distance < 100.0) {
				Log.v("AAADD", "매우 가까움??");
				textBlock.setBgColor(Color.argb(128, 230, 108, 124));
				textBlock.setBorderColor(Color.rgb(255, 104, 91));
			} else {
				//Log.v("AAADD", "FAR??");
				textBlock.setBgColor(Color.argb(128, 107, 214, 157));
				textBlock.setBorderColor(Color.argb(128, 227, 225, 82));
			}
			*/
			//dw.setColor(DataSource.getColor(type));

			textBlock.setBgColor(Color.argb(128, 162, 174, 193));
			textBlock.setBorderColor(Color.argb(128, 227, 225, 82));

			if(distance < 1500.0)
			{
				float currentAngle = MixUtils.getAngle(cMarker.x, cMarker.y,signMarker.x, signMarker.y);
				txtLab.prepare(textBlock);
				dw.setStrokeWidth(1f);
				dw.setFill(true);

				dw.paintObj(txtLab, signMarker.x - txtLab.getWidth() / 2,signMarker.y + maxHeight, currentAngle + 90, 1);
			}
			else if(distance < 600.0)
			{

			}
		}
	}

	public void otherShape(PaintScreen dw) {
		// This is to draw new shape, triangle
		float currentAngle = MixUtils.getAngle(cMarker.x, cMarker.y,
				signMarker.x, signMarker.y);
		float maxHeight = Math.round(dw.getHeight() / 10f) + 1;

		dw.setColor(getColour());
		float radius = maxHeight / 1.5f;
		dw.setStrokeWidth(dw.getHeight() / 100f);
		dw.setFill(false);

		Path tri = new Path();
		float x = 0;
		float y = 0;
		tri.moveTo(x, y);
		tri.lineTo(x - radius, y - radius);
		tri.lineTo(x + radius, y - radius);

		tri.close();
		dw.paintPath(tri, cMarker.x, cMarker.y, radius * 2, radius * 2,
				currentAngle + 90, 1);
	}

}
