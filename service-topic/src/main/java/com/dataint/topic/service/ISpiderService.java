package com.dataint.topic.service;

public interface ISpiderService {
    /**
     * 1. http://192.168.10.78:5000/debug/test_post/save
     *     method:
     *         - POST
     *     params:
     *         - script
     *
     * 2. http://192.168.10.78:5000/update
     *     method:
     *         - POST
     *     params:
     *         - name: 欲修改内容
     *              - 组名: group
     *              - 状态: status
     *              - 频率: rate
     *         - value: 欲修改值
     *              - group: "newGroupName"
     *              - status: "TODO", "STOP", "CHECKING", "DEBUG", "RUNNING"
     *              - rate: "1/3" (rate/burst)
     *         - pk: project name
     * 3. http://192.168.10.78:5000/run
     *     method:
     *         - POST
     *     params:
     *         - project: project name
     */

    void pubDisposeProjects(String keyword);

    void delDisposeProjects();
}
