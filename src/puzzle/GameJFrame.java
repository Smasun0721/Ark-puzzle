package puzzle;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //创建菜单选项条目对象
    //功能
    JMenuItem restartItem = new JMenuItem("重新开始");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem exitItem = new JMenuItem("退出游戏");
    //切换图片
    JMenuItem amiyaItem = new JMenuItem("阿米娅");
    JMenuItem waterItem = new JMenuItem("水月");
    JMenuItem angelItem = new JMenuItem("能天使");
    JMenuItem teItem = new JMenuItem("特蕾西娅");
    //关于
    JMenuItem aboutItem = new JMenuItem("关于我们");

    //创建一个变量以记录图片路径
    String path = "";

    //定义胜利位置数组
    int[][] winPosition = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    //创建二维数组以储存图片位置
    int[][] position = new int[3][3];
    //记录空白图片位置
    int x = 0;
    int y = 0;
    //定义步数变量
    int step = 0;

    //构造方法
    public GameJFrame() {
        //初始化界面
        initWindow();

        //初始化菜单
        initMenu();

        //初始化数据(打乱)
        initData();

        //初始化图片路径
        initPath();

        //初始化图片
        initImage();

        //设置窗口可见
        this.setVisible(true);
    }

    private void initData() {
        //创建随机数对象
        Random rand = new Random();
        //创建数组
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        //打乱数组
        for (int i = 0; i < data.length; i++) {
            int randomIndex = rand.nextInt(data.length);
            int temp = data[i];
            data[i] = data[randomIndex];
            data[randomIndex] = temp;
        }
        //将数组赋值给position
        for (int i = 0; i < 9; i++) {
            if (data[i] == 9) {
                x = i / 3;
                y = i % 3;
                System.out.println(x + " " + y);
            }
            position[i / 3][i % 3] = data[i];
        }
    }

    private void initImage() {
        //删除所有组件
        this.getContentPane().removeAll();
        //判断是否胜利
        if (victory()) {
            JLabel jl = new JLabel(new ImageIcon("image\\win.png"));
            jl.setBounds(275, 300, 197, 73);
            this.getContentPane().add(jl);
        }
        //添加步数标签
        JLabel stepLabel = new JLabel("步数：" + step);
        stepLabel.setBounds(10, 0, 100, 50);
        this.getContentPane().add(stepLabel);
        //添加图片
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //创建图片与标签对象
                JLabel label = new JLabel(new ImageIcon(path + position[i][j] + ".jpg"));
                //设置标签位置
                label.setBounds(250 * j, 50 + 250 * i, 250, 250);
                //设置图片边框
                label.setBorder(new BevelBorder(0));
                //添加标签到窗口
                this.getContentPane().add(label);
            }
        }

        //刷新图片
        this.getContentPane().repaint();

    }

    private void initMenu() {
        //创建菜单栏对象
        JMenuBar menu = new JMenuBar();

        //创建菜单选项对象
        JMenu functionMenu = new JMenu("功能");
        JMenu aboutMenu = new JMenu("关于");
        JMenu changePictureMenu = new JMenu("切换图片");

        //添加条目到菜单选项
        //切换图片
        functionMenu.add(changePictureMenu);
        changePictureMenu.add(amiyaItem);
        changePictureMenu.add(waterItem);
        changePictureMenu.add(angelItem);
        changePictureMenu.add(teItem);
        //功能
        functionMenu.add(restartItem);
        functionMenu.add(reLoginItem);
        functionMenu.add(exitItem);
        //关于
        aboutMenu.add(aboutItem);

        //绑定菜单监听
        //切换图片
        amiyaItem.addActionListener(this);
        waterItem.addActionListener(this);
        angelItem.addActionListener(this);
        teItem.addActionListener(this);
        //功能
        restartItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        exitItem.addActionListener(this);
        aboutItem.addActionListener(this);
        //添加选项到菜单栏
        menu.add(functionMenu);
        menu.add(aboutMenu);

        //设置菜单栏
        this.setJMenuBar(menu);
    }

    private void initWindow() {
        //设置窗口大小
        this.setSize(750, 850);
        //设置窗口标题
        this.setTitle("拼图小游戏 v1.0");
        //设置窗口居中
        this.setLocationRelativeTo(null);
        //设置窗口关闭
        this.setDefaultCloseOperation(3);
        //取消窗口默认布局
        this.setLayout(null);
        //设置键盘监听
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //键盘按下监听
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 69) {
            //删除所有组件
            this.getContentPane().removeAll();
            //添加图片预览
            JLabel jl = new JLabel(new ImageIcon(path + "all.jpg"));
            jl.setBounds(0, 0, 750, 750);
            this.getContentPane().add(jl);
            this.getContentPane().repaint();
        }
    }

    //键盘释放监听
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        //关闭图片预览
        if (code == 69) {
            this.getContentPane().removeAll();
            initImage();
        }
        //判断是否胜利
        if (victory()) {
            return;
        } else if (code == 87) {
            System.out.println("上");
            //判断是否可以上移
            if (x == 2) {
                return;
            }
            //上移
            position[x][y] = position[x + 1][y];
            position[x + 1][y] = 9;
            x++;
            //步数增加
            step++;
            //刷新图片
            initImage();
        } else if (code == 83) {
            System.out.println("下");
            //判断是否可以下移
            if (x == 0) {
                return;
            }
            //下移
            position[x][y] = position[x - 1][y];
            position[x - 1][y] = 9;
            x--;
            //步数增加
            step++;
            //刷新图片
            initImage();
        } else if (code == 65) {
            System.out.println("左");
            //判断是否可以左移
            if (y == 2) {
                return;
            }
            //左移
            position[x][y] = position[x][y + 1];
            position[x][y + 1] = 9;
            y++;
            //步数增加
            step++;
            //刷新图片
            initImage();
        } else if (code == 68) {
            System.out.println("右");
            //判断是否可以右移
            if (y == 0) {
                return;
            }
            //右移
            position[x][y] = position[x][y - 1];
            position[x][y - 1] = 9;
            y--;
            //步数增加
            step++;
            //刷新图片
            initImage();
        }
        //作弊键
        if (code == 80) {
            System.out.println("作弊");
            position = new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            };
            x = 2;
            y = 2;
            initImage();
        }
    }

    //判断是否胜利方法
    private boolean victory() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (position[i][j] != winPosition[i][j]) {
                    return false;
                }
            }
        }
        if(path.equals("image\\arknight\\小特\\")){
            JDialog jDialog = new JDialog(this);
            JLabel jl = new JLabel(new ImageIcon("image\\普瑞塞斯.png"));
            jDialog.getContentPane().add(jl);
            jDialog.getContentPane().repaint();
            //设置窗口大小
            jDialog.setSize(2560, 1440);
            //设置窗口标题
            jDialog.setTitle("PRTS");
            //设置图片大小
            jl.setBounds(0, 0, 2560, 1440);
            //设置窗口居中
            jDialog.setLocationRelativeTo(null);
            //设置窗口不可拉伸
            jDialog.setResizable(false);
            //设置窗口置顶
            jDialog.setAlwaysOnTop(true);
            //设置窗口不关闭就无法执行其他操作
            jDialog.setModal(true);
            //设置窗口可见
            jDialog.setVisible(true);
            return true;
        }else{
            System.out.println("胜利");
            return true;
        }
    }

    //菜单选项点击监听
    @Override
    public void actionPerformed(ActionEvent e) {
        //判断点击的是哪个选项
        if (e.getSource() == restartItem) {
            System.out.println("重新开始");
            //删除所有组件
            this.getContentPane().removeAll();
            //初始化数据
            initData();
            //初始化步数
            step = 0;
            //初始化图片
            initImage();
        } else if (e.getSource() == reLoginItem) {
            System.out.println("重新登录");
            //置顶当前窗口
            this.setAlwaysOnTop(true);
            //打开登录窗口
            new LoginJFrame();
        } else if (e.getSource() == exitItem) {
            System.out.println("退出游戏");
            System.exit(0);
        } else if (e.getSource() == aboutItem) {
            System.out.println("关于我们");
            //创建关于弹框
            JDialog dialog = new JDialog();
            JLabel jl = new JLabel(new ImageIcon("image\\源石.png"));
            dialog.setBounds(0, 0, 920, 920);
            dialog.add(jl);
            //设置关于弹框居中
            dialog.setLocationRelativeTo(null);
            //设置关于弹框不关闭就无法执行其他操作
            dialog.setModal(true);
            //设置弹框无法拉伸
            dialog.setResizable(false);
            //弹框置顶
            dialog.setAlwaysOnTop(true);
            //设置弹框可见
            dialog.setVisible(true);
        }
        //切换图片
        else if (e.getSource() == amiyaItem) {
            //步数清零
            step = 0;
            path = "image\\arknight\\阿米娅\\";
            initData();
            initImage();
        } else if (e.getSource() == waterItem) {
            //步数清零
            step = 0;
            path = "image\\arknight\\水月\\";
            initData();
            initImage();
        } else if (e.getSource() == angelItem) {
            //步数清零
            step = 0;
            path = "image\\arknight\\能天使\\";
            initData();
            initImage();
        } else if (e.getSource() == teItem) {
            //步数清零
            step = 0;
            path = "image\\arknight\\小特\\";
            initData();
            initImage();
        }
    }

    //随机初始化图片路径
    private void initPath() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3);
        String[] pathArr = {"image\\arknight\\阿米娅\\",
                "image\\arknight\\水月\\",
                "image\\arknight\\能天使\\"};
        //随机初始化图片路径
        path = pathArr[randomNum];
    }
}
