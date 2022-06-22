package Test1_web.controller;

import Test1_web.dto.BoardDto;
import Test1_web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String  list(){return  "board/list";}

    @GetMapping("/view/{bno}")
    public String view( @PathVariable("bno") int bno ) {        // { } 안에서 선언된 변수는 밖에 사용불가
        // 1. 내가 보고 있는 게시물의 번호를 세션 저장
        request.getSession().setAttribute("bno", bno);
        return "board/view";
    }
    // 3. 게시물 수정 페이지 열기
    @GetMapping("/update")
    public String update(){ return "board/update";}
    // 4. 게시물 쓰기 페이지 열기
    @GetMapping("/save")
    public String save() { return  "board/save"; }


    /////////////////////////////////////// 2. service 처리 매핑 ///////////////////////////////////////
    // 1. C : 게시물 저장 메소드
    @PostMapping("/save")
    @ResponseBody   // 템플릿 아닌 객체 반환
    public boolean save(BoardDto boardDto ){

        return boardService.save( boardDto );
    }
    // 2. R : 모든 게시물 출력 메소드
    @GetMapping("/getboardlist")
    public void getboardlist(
            HttpServletResponse response ,
            @RequestParam("cno") int cno ,
            @RequestParam("key") String key ,
            @RequestParam("page") int page ,
            //@RequestParam("totalpage") int totalpage,
            @RequestParam("keyword") String keyword){

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(boardService.getboardlist( cno , key , keyword,page  ));
        }catch( Exception e ){ System.out.println( e ); }
    }

    // 2. R2 개별 조회 출력 메소드
    @GetMapping("/getboard")
    public void getboard( HttpServletResponse response){
        int bno =  (Integer) request.getSession().getAttribute("bno");

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboard(  bno   ));
        }catch( Exception e ){
            System.out.println( e );
        }
    }
    // 3. U : 수정 메소드
    @PutMapping("/update")
    @ResponseBody
    public boolean update( BoardDto boardDto ){
        int bno =  (Integer) request.getSession().getAttribute("bno");
        boardDto.setBno( bno );
        return boardService.update( boardDto );
    }
    // 4. D : 삭제 메소드
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete( @RequestParam("bno") int bno ){
        return boardService.delete( bno );
    }

    // 5. 카테고리 출력 메소드
    @GetMapping("/getcategorylist")
    public void getcategorylist( HttpServletResponse response){

        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getcategorylist(   ));
        }catch( Exception e){
            System.out.println(e);
        }

    }


}
