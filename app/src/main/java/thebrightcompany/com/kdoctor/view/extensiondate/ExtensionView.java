package thebrightcompany.com.kdoctor.view.extensiondate;

import java.util.List;

import thebrightcompany.com.kdoctor.model.packages.Package;
import thebrightcompany.com.kdoctor.view.BaseView;

public interface ExtensionView extends BaseView{
    void getListPackages(List<Package> packages);
}
