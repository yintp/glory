package com.yintp.agent.use;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

/**
 * @author yintp
 */
public class BusinessAttach {
    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith("BusinessStarter")) {
                System.out.println("attach vm, name:" + vmd.displayName() + ", pid:" + vmd.id());
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("D:\\agent-api-1.0.jar");
                Thread.sleep(10000L);
                virtualMachine.detach();
            }
        }
    }
}
