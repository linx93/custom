package ${package};

import io.mybatis.mapper.Mapper;
import ${project.attrs.basePackage}.model.${it.name.className};
/**
 * ${it.name} - ${it.comment}
 *
 * @author ${SYS['user.name']}
 */
public interface ${it.name.className}Mapper extends Mapper<${it.name.className}, Long> {

}