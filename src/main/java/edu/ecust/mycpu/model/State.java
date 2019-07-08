package edu.ecust.mycpu.model;

public enum State {
    BLOCK_UP,/*未到达*/
    READY,/*就绪*/
    RUN,/*正在运行*/
    FINISH, /*结束*/
    JAM/*阻塞*/
}
