package koreaIT;

import koreaIT.controller.ArticleController;
import koreaIT.controller.Controller;
import koreaIT.controller.MemberController;

import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);

        Controller controller = null;
        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestData();

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("== 프로그램 종료 ==");
                break;
            }

            String[] cmdBits = cmd.split(" ");
            String controllerName = cmdBits[0];

            if (cmdBits.length == 1) {
                System.out.println("명령어를 확인해주세요.1");
                continue;
            }
            String methodName = cmdBits[1];

            if (controllerName.equals("article")) {
                controller = articleController;

            } else if (controllerName.equals("member")) {
                controller = memberController;
            } else {
                System.out.println("명령어를 확인해주세요.2");
                continue;
            }

            controller.doAction(methodName, cmd);

        }

    }


}
