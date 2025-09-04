import java.util.*;

class Solution {
   public int[] solution(String[] gems) {
       Set<String> allGems = new HashSet<>();
       for(String gem : gems) {
           allGems.add(gem);
       }
       int totalTypes = allGems.size();
       
       Map<String, Integer> window = new HashMap<>();
       int left = 0;
       int minLen = Integer.MAX_VALUE;
       int ansStart = 0, ansEnd = 0;
       
       for(int right = 0; right < gems.length; right++) {
           String gem = gems[right];
           window.put(gem, window.getOrDefault(gem, 0) + 1);
           
           while(window.size() == totalTypes) {
               if(right - left + 1 < minLen) {
                   minLen = right - left + 1;
                   ansStart = left + 1;
                   ansEnd = right + 1;
               }
               
               String leftGem = gems[left];
               window.put(leftGem, window.get(leftGem) - 1);
               if(window.get(leftGem) == 0) {
                   window.remove(leftGem);
               }
               left++;
           }
       }
       
       return new int[]{ansStart, ansEnd};
   }
}