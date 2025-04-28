package koreaIT.controller;

import koreaIT.Container;
import koreaIT.dto.Article;
import koreaIT.dto.Member;
import koreaIT.service.ArticleService;
import koreaIT.service.MemberService;
import koreaIT.util.Util;

import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {

    private Scanner sc;
    private List<Article> articleList;
    private int lastArticleId = 3;
    private List<Member> memberList;

    ArticleService articleService = new ArticleService();
    MemberService memberService = new MemberService();

    public ArticleController(Scanner sc) {
        this.sc = sc;
        this.articleList = articleService.getArticle();
        memberList = memberService.getMembers();
    }

    @Override
    public void doAction(String methodName, String cmd) {
        switch (methodName) {
            case "modify":
                doModify(cmd);
                break;
            case "delete":
                doDelete(cmd);
                break;
            case "detail":
                showDetail(cmd);
                break;
            case "list":
                showList();
                break;
            case "write":
                doWrite();
                break;
            default:
                System.out.println("명령어를 확인해주세요.3");
        }
    }

    private void doModify(String cmd) {

        // parsing start
        String[] cmdBits = cmd.split(" ");

        if (cmdBits.length > 3) {
            System.out.println("명령어를 제대로 입력해주세요.");
        }
        int modifyId = -1;
        try {
            modifyId = Integer.parseInt(cmdBits[2]);
        } catch (NumberFormatException e) {
            System.out.println("정수를 제대로 입력해주세요.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("정수를 추가해서 입력해주세요.");
        }
        // parsing end

        // modifyId 로 게시글 찾아보기
        Article foundArticle = articleService.getArticleById(modifyId);

        if (foundArticle != null) {

            // 로그인 중인 아이디와 게시글의 권한 체크
            if (loginedMember.getId() != foundArticle.getMemberId()){
                System.out.println("해당 게시글에 대한 권한이 없습니다.");
                return;
            }

            System.out.println("기존 제목 : " + foundArticle.getTitle());
            System.out.println("기존 내용 : " + foundArticle.getBody());
            System.out.print("새 제목 : ");
            String newTitle = sc.nextLine().trim();
            System.out.print("새 내용 : ");
            String newBody = sc.nextLine().trim();

            String updateDate = Util.getNowDate();
            foundArticle.setTitle(newTitle);
            foundArticle.setBody(newBody);
            foundArticle.setUpdateDate(updateDate);

            System.out.printf("%d번 게시글이 수정되었습니다.\n", modifyId);
        } else {
            System.out.printf("%d번 게시글은 없습니다\n", modifyId);
        }


    }

    private void doDelete(String cmd) {

        // parsing start
        String[] cmdBits = cmd.split(" ");

        if (cmdBits.length > 3) {
            System.out.println("명령어를 제대로 입력해주세요.");
        }
        int deleteId = -1;
        try {
            deleteId = Integer.parseInt(cmdBits[2]);
        } catch (NumberFormatException e) {
            System.out.println("정수를 제대로 입력해주세요.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("정수를 추가해서 입력해주세요.");
        }
        // parsing end

        // deleteId 로 게시글 찾아보기
        Article foundArticle = articleService.getArticleById(deleteId);

        if (foundArticle != null) {
            // 로그인 중인 아이디와 게시글의 권한 체크
            if (loginedMember.getId() != foundArticle.getMemberId()){
                System.out.println("해당 게시글에 대한 권한이 없습니다.");
                return;
            }
            articleService.remove(foundArticle);
            System.out.printf("%d번 게시글이 삭제되었습니다.\n", deleteId);
        } else {
            System.out.printf("%d번 게시글은 없습니다\n", deleteId);
        }
    }

    private void showDetail(String cmd) {

        // parsing start
        String[] cmdBits = cmd.split(" ");

        if (cmdBits.length > 3) {
            System.out.println("명령어를 제대로 입력해주세요.");
        }
        int detailId = -1;
        try {
            detailId = Integer.parseInt(cmdBits[2]);
        } catch (NumberFormatException e) {
            System.out.println("정수를 제대로 입력해주세요.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("정수를 추가해서 입력해주세요.");
        }
        // parsing end
        Article foundArticle = articleService.getArticleById(detailId);
        if (foundArticle != null) {
            System.out.println("번호 : " + foundArticle.getId());
            System.out.println("등록날짜 : " + foundArticle.getRegDate());
            if (foundArticle.getUpdateDate() != null && !foundArticle.getUpdateDate().isEmpty()) {
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
            }

            String writerName = null;
            for(Member member : memberList){
                if(member.getId() == foundArticle.getMemberId()){
                    writerName = member.getName();
                    break;
                }
            }

            System.out.println("작성자 : " + writerName);
            System.out.println("제목 : " + foundArticle.getTitle());
            System.out.println("내용 : " + foundArticle.getBody());
        } else {
            System.out.printf("%d번 게시글은 없습니다\n", detailId);
        }
    }

    private void showList() {

        List<Article> forPrintArticle =  articleService.getArticle();

        System.out.println("번호  /  작성일     /   작성자   /    제목");
        for (int i = forPrintArticle.size() - 1; i >= 0; i--) {

            String writerName = null;
            for (Member member : memberList){
                if(member.getId() == forPrintArticle.get(i).getMemberId()){
                    writerName = member.getName();
                }
            }


            if (Util.getNowDate().split(" ")[0].equals(forPrintArticle.get(i).getRegDate().split(" ")[0])){
                System.out.printf("%d   / %s    /   %s  /  %s \n", forPrintArticle.get(i).getId(), forPrintArticle.get(i).getRegDate().split(" ")[1], writerName, forPrintArticle.get(i).getTitle());

            }else {
                System.out.printf("%d   / %s    /   %s  /  %s \n", forPrintArticle.get(i).getId(), forPrintArticle.get(i).getRegDate().split(" ")[0], writerName, forPrintArticle.get(i).getTitle());

            }

        }
    }

    private void doWrite() {

        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();
        String regDate = Util.getNowDate();

        lastArticleId++;
        int id = lastArticleId;

        Article addArticle = new Article(id, title, body, regDate, "", loginedMember.getId());

        articleService.add(addArticle);

        System.out.printf("%d번 글이 생성되었습니다.\n", id);
    }



    public void makeTestData() {
        System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
        articleList.add(new Article(1, "제목1", "내용1", "2025-03-12 12:12:12", Util.getNowDate(), 1));
        articleList.add(new Article(2, "제목2", "내용2", "2025-04-12 12:12:12", Util.getNowDate(), 1));
        articleList.add(new Article(3, "제목3", "내용3", "2025-05-12 12:12:12", Util.getNowDate(), 2));
    }
}
