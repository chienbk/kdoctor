package thebrightcompany.com.kdoctor.view.extensiondate.fragment.extension;

import java.util.List;

import thebrightcompany.com.kdoctor.model.packages.Package;
import thebrightcompany.com.kdoctor.view.extensiondate.ExtensionView;

public interface ExtensionDateView extends ExtensionView{

    void getListExtension(List<Package> packages);
}
