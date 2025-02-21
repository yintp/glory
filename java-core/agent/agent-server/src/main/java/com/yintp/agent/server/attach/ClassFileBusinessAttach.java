package com.yintp.agent.server.attach;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.yintp.agent.server.App;

import java.util.List;

/**
 * @author yintp
 */
public class ClassFileBusinessAttach {
    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith(App.class.getName())) {
                System.out.println("attach vm, name:" + vmd.displayName() + ", pid:" + vmd.id());
                /**
                 * 获取本机代码url位置
                 * URL jarUrl = ClassFileBusinessAttach.class.getProtectionDomain().getCodeSource().getLocation();
                 * String jarPath = jarUrl.getPath();
                 */
                VirtualMachine vm = VirtualMachine.attach(vmd.id());
                vm.loadAgent("D:\\agent-api-1.0.jar", "D:\\BusinessService.class");
            }
        }
    }
}
