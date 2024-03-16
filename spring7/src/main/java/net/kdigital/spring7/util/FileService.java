package net.kdigital.spring7.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileService {
    // 1) 서버에 디렉토리가 없으면 디렉토리 생성
    // 2) 원본파일명을 꺼내서 저장파일명(난수값이나 밀리세컨)으로 새로 작성
    // 3) 디렉토리에 파일 저장 작업
    // 4) 저장파일명을 반환하여 DB에 저장할 수 있도록 함

    public static String saveFile(MultipartFile uploadFile, String uploadPath) {

        // 파일이 첨부 되면 디렉토리가 있는지 확안
        // 없으면 생성, 있으면 그대로 사용
        if(!uploadFile.isEmpty()) {
            File path = new File(uploadPath);
            if(!path.isDirectory()) { // 디렉토리가 없다면
                path.mkdirs(); // 디렉토리 생성
            }

            // 원본파일명 추출
            String originalFileName = uploadFile.getOriginalFilename();
            
            // 랜덤값 발생
            String uuid = UUID.randomUUID().toString();

            // 원본파일의 이름과 확장자명 분리
            String filename; // 파일명
            String ext; // 확장자명
            String savedFileName; // 저장파일명

            // .의 위치 반환, 확장자가 없는 파일이면 -1 반환
            int position = originalFileName.lastIndexOf('.'); 
            // 확장자가 없을 경우
            if(position == -1) ext = ""; 
            // 확장자가 있을 경우
            else ext = "." + originalFileName.substring(position + 1);

            filename = originalFileName.substring(0, position);
            savedFileName = filename + "_" + uuid + ext;

            // 서버의 저장공간에 파일 저장하기
            File serverFile = null;
            serverFile = new File(uploadPath + "/" + savedFileName);

            try {
                uploadFile.transferTo(serverFile);
            } catch (Exception e) {
                savedFileName = null; // 오류발생시 DB에도 저장되면 안됨. null로 바꿔야
                e.printStackTrace();
            }

            return savedFileName;

        }
        return null; // 저장파일명 반환
    }

    /**
     * 저장장치에 저장된 파일 삭제
     * @param fullPath (경로+파일명)
     * @return
     */
    public static boolean deleteFile(String fullPath) {
        boolean result = false;
        
        File delFile = new File(fullPath);
        // 진짜 파일일 경우
        if(delFile.isFile()) { 
            result = delFile.delete(); // true: 성공적으로 삭제 완료되면 반환
        }

        return result;
    };
}
