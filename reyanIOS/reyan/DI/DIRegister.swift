
import Resolver
import nativeShared

extension Resolver {
    
//    public static func registerNetworkServices() {
//
//    }
    
    public static func registerSettingsScreen() {
        
        // 1
        register{
            SettingsPresenter(processor: resolve(name : "SettingsProcessor"))
        }
        
        // 2
        register(MviProcessor.self, name: "SettingsProcessor") {
            SettingsProcessor(settingsRepo: resolve(), calligraphyRepo: resolve(), uiMapper: resolve(name : "CalligraphyDomainUIMapper"))
        }
    }
    
    public static func registerRepos() {
        
        // 2-1
        register{
            SettingRepoImpl(settingsDataSource : resolve(), calligraphyDS : resolve(), mapper : resolve(name : "CalligraphyEntityRepoMapper"))
        }.implements(SettingRepo.self)
        
        // 2-2
        register{
            CalligraphyRepoImpl(ds: resolve(), mapper: resolve())
        }.implements(CalligraphyRepo.self)
    }
    
    public static func registerDataSources() {
        
        // 2-1-1
        register {
            SettingsDataSourceImpl(queries: resolve())
        }.implements(SettingsDataSource.self)
        
        // 2-1-2
        // 2-2-1
        register {
            CalligraphyLocalDataSourceImpl(queries: resolve())
        }.implements(CalligraphyLocalDataSource.self)
    }
    
    public static func registerQueries() {
        
        let mainDB : Main = resolve()

        // 2-1-1-1
        register(SettingsQueries.self) {
            return mainDB.settingsQueries
        }
       
        // 2-1-2-1
        // 2-2-1-1
        register(CalligraphyQueries.self) {
            return mainDB.calligraphyQueries
        }
    }
    
    public static func registerMappers() {
        // 2-1-3
        // 2-2-2
        register(Mapper.self, name: "CalligraphyEntityRepoMapper") {
            CalligraphyEntityRepoMapper()
        }
        
        // 2-3
        register(Mapper.self, name: "CalligraphyDomainUIMapper"){
            CalligraphyDomainUIMapper()
        }
    }
    
    public static func registerNativeDatabase() {
        
        register(Main.self){
            NativeDatabaseFactory().create()
        }
    }
    
    
    
}
