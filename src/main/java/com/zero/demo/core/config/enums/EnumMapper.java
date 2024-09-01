package com.zero.demo.core.config.enums;

import lombok.ToString;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ToString
public class EnumMapper {

   private Map<String, List<EnumMapperValue>> mapList = new LinkedHashMap<>();

   public EnumMapper() {}

   public void put(String key, Class<? extends EnumMapperType> e) {
       mapList.put(key, toEnumValues(e));
   }

   //동적(dynamic) ENUM 변수에 code, title 불러오기
   private List<EnumMapperValue> toEnumValues (Class<? extends EnumMapperType> e) {
       return Arrays.stream(e.getEnumConstants()).map(EnumMapperValue::new)
       .collect((Collectors.toList()));
   }

   public List<EnumMapperValue> get(String key) {
       return mapList.get(key);
   }

   public Map<String, List<EnumMapperValue>> get(List<String> keys) {
       if (keys == null || keys.isEmpty()) {
           return new LinkedHashMap<>();
       }
       return keys.stream().collect(Collectors.toMap(Function.identity(), key -> mapList.get(key)));
   }

   public Map<String, List<EnumMapperValue>> getAll() {return mapList;}
}
