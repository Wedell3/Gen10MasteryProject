package mastery_project.domain;

import mastery_project.models.Host;
import mastery_project.repository.HostRepository;

import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {
    List<Host> all = makeHosts();
    @Override
    public List<Host> findAll() {
        return all;
    }

    @Override
    public Host findByEmail(String email) {
        if(all.get(0).getEmail().equals(email)) {
            return all.get(0);
        }
        return null;
    }

    @Override
    public Host findById(String id) {
        if (all.get(1).getId().equals(id)){
            return all.get(1);
        }
        return null;
    }

    @Override
    public Host addHost(Host host) {
        return host;
    }

    @Override
    public boolean updateHost(Host host) {
        return host.getId().equals("test-test-test");
    }

    @Override
    public boolean deleteHost(String email) {
        return email.equals("host@host.com");
    }

    private ArrayList<Host> makeHosts() {
        Host host1 = new Host();
        host1.setEmail("host@host.com");
        Host host2 = new Host();
        host2.setId("test-test-test");
        ArrayList<Host> hosts = new ArrayList<>();
        hosts.add(host1);
        hosts.add(host2);
        return hosts;
    }


}
