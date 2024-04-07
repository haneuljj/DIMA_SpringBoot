package net.kdigital.spring7.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageNavigator {
    private final int pagePerGroup = 10; // 그룹당 페이지 수
    private int pageLimit; // 페이지 당 글 개수
    private int page; // 사용자가 요청한 페이지
    private int totalPages; // 총 페이지 개수 (글 개수 157개 -> 총 16페이지)
    private int totalGroupCount; // 총 그룹의 개수
    private int currentGroup; // 요청한 페이지가 속한 그룹
    private int startPageGroup;
    private int endPageGroup;
    // <<  < 1 2 3 4 5 6 7 8 9 10 >  >>

    public PageNavigator(int pageLimit, int page, int totalPages) {
        // 멤버 초기화
        this.pageLimit = pageLimit;
        this.page = page;
        this.totalPages = totalPages;

        // 총 그룹 수 계산
        totalGroupCount = totalPages / pagePerGroup;
        // 위 계산에서 나머지가 존재하면 나머지 페이지를 담을 그룹이 하나 더 있어야함
        totalGroupCount += (totalPages % pagePerGroup == 0) ? 0 : 1; 

        // 사용자가 요청한 페이지의 첫번째 번호와 마지막 번호 계산
        startPageGroup = ((int)(Math.ceil(((double)page / pageLimit))) - 1) * pageLimit + 1;
        endPageGroup = ((startPageGroup + pageLimit - 1) < totalPages) 
                    ? (startPageGroup + pageLimit - 1) 
                    : totalPages;
        
        // 검색과 함께 사용했는데 검색결과가 하나도 없으면
        // startPageGroup=1, endPageGroup=0 으로 됨
        // 그런 경우 endPageGroup=1로
        if(endPageGroup == 0) endPageGroup = 1;

        // 요청한 페이지가 속한 그룹 계산
        // (11-1) / 10 + 1 = 2, 2그룹
        currentGroup = (page - 1) / pagePerGroup + 1;
    }   
}
