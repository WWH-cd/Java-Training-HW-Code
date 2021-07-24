package beanDemo;

import beanDemo.pojos.Employee;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class BeanDefinitionRegistryPostProcessorImpl implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println(" ===> postProcessBeanDefinitionRegistry: \n"
                + beanDefinitionRegistry.getBeanDefinitionCount());
        System.out.println(" ===> postProcessBeanDefinitionRegistry: \n"
            + String.join(",", beanDefinitionRegistry.getBeanDefinitionNames()));

        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Employee.class);
        rootBeanDefinition.getPropertyValues().add("name", "Andrea");
        rootBeanDefinition.getPropertyValues().add("position", "frontdesk");
        beanDefinitionRegistry.registerBeanDefinition("team-Alpha", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println(" ===> postProcessBeanFactory: \n"
                + configurableListableBeanFactory.getBeanDefinitionCount());
        System.out.println(" ===> postProcessBeanFactory: \n"
                + String.join(",", configurableListableBeanFactory.getBeanDefinitionNames()));
        configurableListableBeanFactory.registerSingleton("team-Beta", new Employee());

    }
}
