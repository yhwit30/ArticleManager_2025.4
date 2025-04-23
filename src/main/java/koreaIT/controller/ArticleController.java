package koreaIT.controller;

import koreaIT.dto.Article;
import koreaIT.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {

    private Scanner sc;
    private List<Article> articleList;
    private int lastArticleId = 3;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        this.articleList = new ArrayList<>();
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

    public void doModify(String cmd) {

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

        Article foundArticle = getArticleById(modifyId);

        if (foundArticle != null) {
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

    public void doDelete(String cmd) {

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
        Article foundArticle = getArticleById(deleteId);
        if (foundArticle != null) {
            articleList.remove(foundArticle);
            System.out.printf("%d번 게시글이 삭제되었습니다.\n", deleteId);
        } else {
            System.out.printf("%d번 게시글은 없습니다\n", deleteId);
        }
    }

    public void showDetail(String cmd) {

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
        Article foundArticle = getArticleById(detailId);
        if (foundArticle != null) {
            System.out.println("번호 : " + foundArticle.getId());
            System.out.println("등록날짜 : " + foundArticle.getRegDate());
            if (foundArticle.getUpdateDate() != null && !foundArticle.getUpdateDate().isEmpty()) {
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
            }
            System.out.println("제목 : " + foundArticle.getTitle());
            System.out.println("내용 : " + foundArticle.getBody());
        } else {
            System.out.printf("%d번 게시글은 없습니다\n", detailId);
        }
    }

    public void showList() {
        System.out.println("번호  /  제목  / 내용");
        for (int i = articleList.size() - 1; i >= 0; i--) {
            System.out.printf("%d   / %s    / %s\n", articleList.get(i).getId(), articleList.get(i).getTitle(), articleList.get(i).getBody());
        }
    }

    public void doWrite() {
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();
        String regDate = Util.getNowDate();

        lastArticleId++;
        int id = lastArticleId;

        Article addArticle = new Article(id, title, body, regDate, "");

        articleList.add(addArticle);

        System.out.printf("%d번 글이 생성되었습니다.\n", id);
    }

    public Article getArticleById(int id) {
        for (Article article : articleList) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    public void makeTestData() {
        System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
        articleList.add(new Article(1, "제목1", "내용1", "2025-03-12 12:12:12", Util.getNowDate()));
        articleList.add(new Article(2, "제목2", "내용2", "2025-04-12 12:12:12", Util.getNowDate()));
        articleList.add(new Article(3, "제목3", "내용3", "2025-05-12 12:12:12", Util.getNowDate()));
    }
}
