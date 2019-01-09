/*
 * Copyright (C) 2012- Peer internet solutions & Finalist IT Group
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
package org.stampar.data.convert;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.stampar.POIMarker;
import org.stampar.data.DataHandler;
import org.stampar.data.DataSource;
import org.stampar.lib.marker.Marker;

//import no.nordicsemi.android.nrfbeacon.beacon.BeaconsFragment;
//import no.nordicsemi.android.nrfbeacon.dfu.DfuFragment;
//import no.nordicsemi.android.nrfbeacon.update.UpdateFragment;


/**
 * A data processor for wikipedia urls or data, Responsible for converting raw data (to json and then) to marker data.
 * @author A. Egal
 */
public class WikiDataProcessor extends DataHandler implements DataProcessor{

	public static final int MAX_JSON_OBJECTS = 1000;

	// 안동환이 비콘 컴파일을 위해 추가함.
	
	
	@Override
	public String[] getUrlMatch() {
		String[] str = {"wiki"};
		return str;
	}

	@Override
	public String[] getDataMatch() {
		String[] str = {"wiki"};
		return str;
	}
	
	@Override
	public boolean matchesRequiredType(String type) {
		if(type.equals(DataSource.TYPE.WIKIPEDIA.name())){
			return true;
		}
		return false;
	}

	@Override
	public List<Marker> load(String rawData, int taskId, int colour) throws JSONException {
		List<Marker> markers = new ArrayList<Marker>();


		/*
		JSONObject root = convertToJSON(rawData);
		JSONArray dataArray = root.getJSONArray("geonames");
		int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());
		//Log.v("DONGDONG", "BBBBBBBB");
		for (int i = 0; i < top; i++) {
			JSONObject jo = dataArray.getJSONObject(i);
			
			Marker ma = null;
			if (jo.has("title") && jo.has("lat") && jo.has("lng")
					&& jo.has("elevation") && jo.has("wikipediaUrl")) {
	
				//Log.v(MixView.TAG, "processing Wikipedia JSON object");
		
				//no unique ID is provided by the web service according to http://www.geonames.org/export/wikipedia-webservice.html
				ma = new POIMarker(
						"",
						HtmlUnescape.unescapeHTML(jo.getString("title"), 0), 
						jo.getDouble("lat"), 
						jo.getDouble("lng"), 
						jo.getDouble("elevation"), 
						"http://"+jo.getString("wikipediaUrl"), 
						taskId, colour);
						//markers.add(ma);

				Log.v("DDDDD", String.valueOf(taskId));
			}
		}
		// 새로 추가하는 부분, 왜 추가가 안 될까
		// 300번 인 경우 사진을 출력해주자.
		// 아래의 양식을 따르면 됨..
		/*
		Marker ma = null;
		ma = new POIMarker(
				"300",
				"피피카츄",
				35.864269,
				128.549479,
				35,
				"http://www.01095117798.com",
				taskId, colour);
		markers.add(ma);
		*/
		//dongsAdd(markers);
		dongsSikAdd(markers);
		dongsCoffeAdd(markers);
		// 최종 릴리즈 때 제외시킬 것.
		dongsTestAdd(markers);

		return markers;
	}
	private void dongsAdd(List<Marker> markers)
	{
		// 테스트 환경 1번
		//dongsMarkerAdd("200", "Hello World", 35.864269, 128.549479, "http://www.01095117798.com", markers);
		//dongsMarkerAdd("300", "음식집", 35.866908, 128.551685, "http://www.01095117798.com", markers);
		//dongsMarkerAdd("300", "조금 멈", 35.864950, 128.549925, "http://www.01095117798.com", markers);
		//dongsMarkerAdd("100", "가까움", 35.867093, 128.549925, "http://www.01095117798.com", markers);
	}
	private void dongsSikAdd(List<Marker> markers)
	{
		dongsMarkerAdd("100", "금호 식당", 35.852548, 128.463152, "http://www.01095117798.com/dongs-page-1-1", markers);
		dongsMarkerAdd("100", "경산 식당", 35.842545, 128.463347, "http://www.01095117798.com/dongs-page-1-2", markers);
		dongsMarkerAdd("100", "강정강나루", 35.843057, 128.464217, "http://www.01095117798.com/dongs-page-1-3", markers);
		dongsMarkerAdd("100", "중앙 떡볶이", 35.869457, 128.597006, "http://www.01095117798.com/dongs-page-2-1", markers);
		dongsMarkerAdd("100", "봉추 찜닭", 35.869187, 128.596593, "http://www.01095117798.com/dongs-page-2-2", markers);
		dongsMarkerAdd("100", "카레 센타", 35.869359, 128.596935, "http://www.01095117798.com/dongs-page-2-3", markers);
		dongsMarkerAdd("100", "또바기\n키친바", 35.860781, 128.606688, "http://www.01095117798.com/dongs-page-3-1", markers);
		dongsMarkerAdd("100", "닭 한끼", 35.861168, 128.606774, "http://www.01095117798.com/dongs-page-3-2", markers);
		dongsMarkerAdd("100", "오 짱", 35.860559, 128.606975, "http://www.01095117798.com/dongs-page-3-3", markers);
		dongsMarkerAdd("100", "내생의봄날", 35.850996, 128.557307, "http://www.01095117798.com/dongs-page-4-1", markers);
		dongsMarkerAdd("100", "서울왕족발", 35.852807, 128.554442, "http://www.01095117798.com/dongs-page-4-2", markers);
		dongsMarkerAdd("100", "동해회타운", 35.852789, 128.555887, "http://www.01095117798.com/dongs-page-4-3", markers);
	}
	private void dongsCoffeAdd(List<Marker> markers)
	{
		dongsMarkerAdd("200", "필레 몬스", 35.843673, 128.466193, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "파스 꾸지", 35.841308, 128.465394, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "엔젤리너스", 35.868936, 128.597078, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "파스 꾸지", 35.869491, 128.596888, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "카페 숲", 35.86992, 128.597069, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "루당", 35.860606, 128.60683, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "커피 명가", 35.860189, 128.606549, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "플랫폼", 35.859814, 128.606287, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "씨애틀에 잠못 이루는 밤", 35.850993, 128.557308, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "카페 라떼", 35.85215, 128.555036, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "하바나 익스프레스", 35.852932, 128.556107, "http://www.01095117798.com/dongs-page-5", markers);
	}
	private void dongsTestAdd(List<Marker> markers)
	{
		dongsMarkerAdd("301", "강정보", 35.843395, 128.465313, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("302", "228 공원", 35.868872, 128.597786, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("303", "김광석 거리", 35.861142, 128.606825, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("304", "두류 공원", 35.848321, 128.558160, "http://www.01095117798.com/dongs-page-5", markers);

		dongsMarkerAdd("304", "두류 공원2", 35.867304, 128.551935, "http://www.01095117798.com/dongs-page-5", markers);
		dongsMarkerAdd("200", "카페", 35.868038, 128.550955, "http://www.01095117798.com/dongs-page-1-1", markers);
		dongsMarkerAdd("100", "식당", 35.868338, 128.550983, "http://www.01095117798.com/dongs-page-5", markers);


		//dongsMarkerAdd("100", "1km 이외", 35.869272, 128.565069, "http://www.01095117798.com/dongs-page-5", markers);
		//dongsMarkerAdd("100", "1km 이외", 35.871654, 128.565326, "http://www.01095117798.com/dongs-page-5", markers);
	}
	private void dongsHotAdd(List<Marker> markers)
	{

	}


	public void dongsMarkerAdd(String id, String title, double lat, double rat, String url, List<Marker> markers)
	{
		Marker ma = null;
		ma = new POIMarker(
				id,
				title,
				lat,
				rat,
				35,
				url,
				-1, 10);
		markers.add(ma);
	}

	
	private JSONObject convertToJSON(String rawData){
		try {
			return new JSONObject(rawData);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
