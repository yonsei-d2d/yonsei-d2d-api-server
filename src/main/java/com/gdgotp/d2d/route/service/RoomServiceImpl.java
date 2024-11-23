package com.gdgotp.d2d.route.service;

import com.gdgotp.d2d.error.InvalidRouteLocationException;
import com.gdgotp.d2d.location.model.Location;
import com.gdgotp.d2d.location.service.LocationService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RoomServiceImpl implements RoomService {
    private final LocationService locationService;

    public RoomServiceImpl(LocationService locationService) {
        this.locationService = locationService;
    }

    private static final Map<String, Pair<String, Boolean>> buildingMap = new HashMap<>();

    static {
        // 정문 일대
        buildingMap.put("공A", new Pair<>("제1공학관", false));
        buildingMap.put("공N", new Pair<>("제1공학관", false));
        buildingMap.put("공S", new Pair<>("제1공학관", false));
        buildingMap.put("공B", new Pair<>("제2공학관", false));
        buildingMap.put("공C", new Pair<>("제3공학관", false));
        buildingMap.put("공D", new Pair<>("제4공학관", false));

        // 서문, 남문 일대
        buildingMap.put("스포츠", new Pair<>("스포츠과학관", false));
        buildingMap.put("야구장", new Pair<>("야구장", false));
        buildingMap.put("운동장", new Pair<>("운동장", false));
        buildingMap.put("첨", new Pair<>("첨단과학관", false));
        buildingMap.put("IBS", new Pair<>("IBS관", false));
        buildingMap.put("과", new Pair<>("과학관", false));
        buildingMap.put("과B", new Pair<>("과학관", true));
        buildingMap.put("과S", new Pair<>("과학원", false));
        buildingMap.put("과SB", new Pair<>("과학원", true));
        buildingMap.put("삼", new Pair<>("삼성관", false));
        buildingMap.put("삼B", new Pair<>("삼성관", true));
        buildingMap.put("체", new Pair<>("체육교육관", false));
        buildingMap.put("체조장", new Pair<>("체육교육관", false));
        buildingMap.put("무용실", new Pair<>("체육교육관", false));

        // 중앙 일대
        buildingMap.put("음", new Pair<>("음악관", false));
        buildingMap.put("윤주용홀", new Pair<>("음악관", false));
        buildingMap.put("중입자", new Pair<>("중입자치료센터", false));
        buildingMap.put("루", new Pair<>("루스채플", false));
        buildingMap.put("원", new Pair<>("루스채플", false));
        buildingMap.put("원B", new Pair<>("루스채플", true));

        // 중도 이북
        buildingMap.put("교", new Pair<>("교육과학관", false));
        buildingMap.put("경영", new Pair<>("경영관", false));
        buildingMap.put("빌", new Pair<>("빌링슬리관", false));
        buildingMap.put("성", new Pair<>("성암관", false));
        buildingMap.put("외", new Pair<>("외솔관", false));
        buildingMap.put("유", new Pair<>("유억겸기념관", false));
        buildingMap.put("광", new Pair<>("광복관", false));
        buildingMap.put("광B", new Pair<>("광복관", true));
        buildingMap.put("광별", new Pair<>("광복관별관", false));
        buildingMap.put("상본", new Pair<>("대우관", false));
        buildingMap.put("상별", new Pair<>("대우관별관", false));
        buildingMap.put("대별", new Pair<>("대우관별관", false));
        buildingMap.put("대별B", new Pair<>("대우관별관", true));
        buildingMap.put("백", new Pair<>("백양관", false));
        buildingMap.put("백N", new Pair<>("백양관", false));
        buildingMap.put("백S", new Pair<>("백양관", false));
        buildingMap.put("신", new Pair<>("원두우신학관", false));
        buildingMap.put("신예배실", new Pair<>("원두우신학관", false));
        buildingMap.put("신B", new Pair<>("원두우신학관", true));
        buildingMap.put("연", new Pair<>("연희관", false));
        buildingMap.put("위", new Pair<>("위당관", false));
        buildingMap.put("위B", new Pair<>("위당관", true));

        // 동문 일대
        buildingMap.put("미우", new Pair<>("미우관", false));
        buildingMap.put("새천", new Pair<>("새천년관", false));

        // 북문 일대
        buildingMap.put("테니스장", new Pair<>("테니스장", false));
    }

    @Override
    public Location findLocationByRoom(String input) {
        String regex = "^([가-힣A-Za-z]+)(\\d+)(.*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        String prefix = null;
        String number = null;
        String rest = null;
        if (matcher.find()) {
            prefix = matcher.group(1);
            number = matcher.group(2);
            rest = matcher.group(3);
        }

        if (prefix == null) throw new InvalidRouteLocationException();

        prefix = prefix.toUpperCase();
        var parsedPrefix = buildingMap.getOrDefault(prefix, null);

        if (parsedPrefix == null) throw new InvalidRouteLocationException();

        String building = parsedPrefix.a;
        boolean isBasement = parsedPrefix.b;

        Location location = locationService.findLocationByName(building);

        if (location == null) throw new InvalidRouteLocationException();

        location.setName(location.getName().concat(" (" + input + ")"));

        // 호수와 무관한 건물
        switch (prefix) {
            case "스포츠", "야구장", "운동장", "테니스장" -> {
                location.setLevel(0);
                return location;
            }
            case "윤주용홀" -> {
                location.setLevel(1);
                return location;
            }
            case "원" -> {
                location.setLevel(-1);
                return location;
            }
            case "신예배실" -> {
                location.setLevel(2);
                return location;
            }
            case "체조장" -> {
                location.setLevel(3);
                return location;
            }
            case "무용실" -> {
                location.setLevel(4);
                return location;
            }
        }

        if (number.length() < 2 || number.length() > 3) throw new InvalidRouteLocationException();
        location.setLevel(getLevel(number, building, isBasement));
        return location;
    }

    private static int getLevel(String number, String building, boolean isBasement) {
        int level = 0;
        if (number.startsWith("0")) {
            if (building.equals("제2공학관") ||
                    building.equals("제3공학관") ||
                    building.equals("연")
            ) {
                level = -1;
            }
            else if (building.equals("외솔관")) {
                if (number.equals("01") || number.equals("02")) level = 2;
            }
            else throw new InvalidRouteLocationException();
        } else {
            level = isBasement ? -Integer.parseInt(number.substring(0, 1)) : Integer.parseInt(number.substring(0, 1));
        }
        return level;
    }
}
