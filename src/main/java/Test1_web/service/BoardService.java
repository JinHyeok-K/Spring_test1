package Test1_web.service;


import Test1_web.domain.board.BoardEntity;
import Test1_web.domain.board.BoardRepository;
import Test1_web.domain.board.CategoryEntity;
import Test1_web.domain.board.CategoryRepository;
//import Test1_web.domain.user.UserRepository;
import Test1_web.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpServletRequest request;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    // c
    @Transactional
    public boolean save(BoardDto boardDto){

        boolean sw = false;
        int cno = 0;
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        for (CategoryEntity entity : categoryEntityList){
            if (entity.getCname().equals(boardDto.getCategory())){
                sw =true;
                cno = entity.getCno();
            }
        }
        CategoryEntity categoryEntity = null;
        if(!sw){
            categoryEntity = CategoryEntity.builder()
                    .cname(boardDto.getCategory())
                    .build();
            categoryRepository.save(categoryEntity);
        }else{
            categoryEntity = categoryRepository.findById(cno).get();
        }

        BoardEntity boardEntity = boardRepository.save(boardDto.toentity());
        //boardEntity.setUserEntity();
        boardEntity.setCategoryEntity(categoryEntity);

        categoryEntity.getBoardEntityList().add(boardEntity);

        return true;

    }

    // R
    public JSONObject getboardlist( int cno, String key, String keyword,int page){

        JSONObject object = new JSONObject();

        Page<BoardEntity> boardEntityList = null;
        Pageable pageable = PageRequest.of(page,3 , Sort.by(Sort.Direction.DESC,"bno")); //SQL : limit 과 동일한 기능처리 +@

        if(key.equals("btitle")){
            boardEntityList = boardRepository.findBybtitle(cno,keyword,pageable);

        }else if(key.equals("bcontent")){
            boardEntityList = boardRepository.findBybcontent(cno,keyword,pageable);
        }else{
            boardEntityList = boardRepository.findBybtitle(cno,keyword,pageable);
        }

        int btncount = 5;
        int startbtn = (page/btncount)*btncount+1;
        int endbtn = startbtn+btncount -1;
        if(endbtn> boardEntityList.getTotalPages()) endbtn = boardEntityList.getTotalPages();


        JSONArray jsonArray = new JSONArray();
        for(BoardEntity entity : boardEntityList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bno",entity.getBno());
            jsonObject.put("btitle",entity.getBtitle());
            jsonObject.put("bindate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            jsonObject.put("bview", entity.getBview());
            jsonObject.put("blike", entity.getBlike());
            jsonObject.put("bid",entity.getBid());
            jsonObject.put("bpassword",entity.getBpassword());
//            jsonObject.put("mid", entity.getUserEntity().getMid());
            jsonArray.put(jsonObject);
        }

        object.put("startbtn",startbtn);
        object.put("endbtn",endbtn);
        object.put("totalpages",boardEntityList.getTotalPages());
        object.put("data",jsonArray);
        return object;
    }

    // R : solo
    @Transactional
    public JSONObject getboard(int bno){
        String ip = request.getRemoteAddr();
        Optional<BoardEntity> optional =  boardRepository.findById( bno );
        BoardEntity entity = optional.get();

        Object com =  request.getSession().getAttribute(ip+bno);
        if( com == null  ){ // 만약에 세션이 없으면
            // ip 와 bno 합쳐서 세션(서버내 저장소) 부여
            request.getSession().setAttribute(ip+bno , 1 );
            request.getSession().setMaxInactiveInterval( 60*60*24  ); // 세션 허용시간 [ 초단위  ]
            // 조회수 증가처리
            entity.setBview( entity.getBview()+1 );
        }

        JSONObject object = new JSONObject();
        object.put("bno" , entity.getBno() );
        object.put("btitle" , entity.getBtitle() );
        object.put("bcontent" , entity.getBcontent() );
        object.put("bview" , entity.getBview() );
        object.put("blike" , entity.getBlike() );
        object.put("bindate" , entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) );
        object.put("bmodate" , entity.getModifiedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) );
        object.put("bid",entity.getBid());
        object.put("bpassword",entity.getBpassword());
        return object;

    }

    // 3. U
    @Transactional
    public  boolean update(BoardDto boardDto){
        Optional<BoardEntity> optionalBoard
                = boardRepository.findById(boardDto.getBno());
        BoardEntity boardEntity = optionalBoard.get();
        boardEntity.setBtitle(boardDto.getBtitle());
        boardEntity.setBcontent(boardDto.getBcontent());
    return true;
    }
    // 4. D
    @Transactional
    public boolean delete(int bno){
        BoardEntity boardEntity = boardRepository.findById(bno).get();
        boardRepository.delete(boardEntity);
        return true;
    }
    // 5.
    public JSONArray getcategorylist(){
        List<CategoryEntity> categoryEntityList
                = categoryRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(CategoryEntity entity : categoryEntityList){
            JSONObject object = new JSONObject();
            object.put("cno",entity.getCno());
            object.put("cname",entity.getCname());
            jsonArray.put(object);


        }
        return jsonArray;
    }

}
