
import Resolver
import nativeShared

extension Resolver {
    
    public static func registerScreens() {
        
        //  QuranHomeModule
        register{
            QuranHomePresenter(processor: resolve(name : "QuranHomeProcessor"))
        }
        register(MviProcessor.self, name: "QuranHomeProcessor") {
            QuranHomeProcessor(navigator: resolve())
        }
        
        // JuzListModule
        register{
            JuzListView(presenter: resolve())
        }
        register{
            JuzListPresenter(processor: resolve(name : "JuzListProcessor"))
        }
        register(MviProcessor.self, name: "JuzListProcessor") {
            JuzListProcessor(navigator: resolve(), juzListUsecase : resolve(), juzMapper : resolve(name : "JuzRepoUIMapper"))
        }
        
        // SurahListModule
        register{
            SurahListView(presenter: resolve())
        }
        register{
            SurahListPresenter(processor: resolve(name : "SurahListProcessor"))
        }
        register(MviProcessor.self, name: "SurahListProcessor") {
            SurahListProcessor(navigator: resolve(), surahUsecase : resolve(), surahMapper : resolve(name : "SurahRepoUIMapper"))
        }

        // SearchModule
        register{
            SearchPresenter(processor: resolve(name : "SearchProcessor"))
        }
        register(MviProcessor.self, name: "SearchProcessor") {
            SearchProcessor(searchUsecase : resolve(), navigator: resolve(), mapper : resolve(name : "SurahRepoUIMapper"))
        }
        
        // SurahModule
        register{
            SurahPresenter(processor: resolve(name : "SurahProcessor"))
        }
        register(MviProcessor.self, name: "SurahProcessor") {
            SurahProcessor(navigator: resolve(),
                           getAyaUseCase : resolve(),
                           ayaMapper : resolve(name : "AyaRepoUIMapper"),
                           surahRepoHeaderMapper : resolve(name : "SurahRepoHeaderMapper"),
                           settingRepo : resolve(),
                           surahUsecase : resolve(), settingsProcessor: resolve(name : "SurahSettingsProcessor")
            )
        }
        register(MviProcessor.self, name: "SurahSettingsProcessor") {
            SurahSettingsProcessor(settingsRepo: resolve(), calligraphyRepo: resolve(), uiMapper: resolve(name : "CalligraphyDomainUIMapper"))
        }
        
        // SettingsModule
        // 1
        register{
            SettingsPresenter(processor: resolve(name : "SettingsProcessor"))
        }
        // 2
        register(MviProcessor.self, name: "SettingsProcessor") {
            SettingsProcessor(settingsRepo: resolve(), calligraphyRepo: resolve(), uiMapper: resolve(name : "CalligraphyDomainUIMapper"))
        }
    }
    
    public static func registerUseCases() {
        
        register(GetAyaUseCase.self){
            GetAyaUseCaseImpl(ayaListRepo : resolve(), settingRepo: resolve(), calligraphyRepo : resolve())
        }
        
        register(GetSurahUsecase.self){
            GetSurahUsecaseImpl(surahRepo : resolve(), settingRepo: resolve(), calligraphyDS : resolve())
        }
        
        register(JuzListUsecase.self){
            JuzListUsecaseImpl(juzListRepo : resolve(), settingRepo: resolve())
        }
        
        register(JuzListUsecase.self){
            JuzListUsecaseImpl(juzListRepo : resolve(), settingRepo: resolve())
        }
        
        register(SearchSurahNameUseCase.self){
            SearchSurahNameUseCaseImpl(settingRepo : resolve(), searchRepo: resolve(), calligraphyDS: resolve())
        }
    }
    
    public static func registerRepos() {
        
        // 2-1
        register(SettingRepo.self){
            SettingRepoImpl(settingsDataSource : resolve(), calligraphyDS : resolve(), mapper : resolve(name : "CalligraphyEntityRepoMapper"))
        }
        
        // 2-2
        register(CalligraphyRepo.self){
            CalligraphyRepoImpl(ds: resolve(), mapper: resolve(name : "CalligraphyEntityRepoMapper"))
        }
        
        register(AyaListRepo.self){
            AyaListRepoImpl(dataSource: resolve(), ayaMapper: resolve(name : "AyaEntityRepoMapper"))
        }
        
        register(JuzListRepo.self){
            JuzListRepoImpl(ayaDataSource: resolve(), juzMapper: resolve(name : "JuzEntityRepoMapper"))
        }
        
        register(SurahSearchRepo.self){
            SurahSearchRepoImpl(dataSource: resolve(), mapper: resolve(name : "SurahWithTwoNameEntityRepoMapper"))
        }
        
        register(SurahRepo.self){
            SurahRepoImpl(dataSource: resolve(), twoNameMapper: resolve(name : "SurahWithTwoNameEntityRepoMapper"))
        }
        
    }
    
    public static func registerDataSources() {
        
        
        register(SurahLocalDataSource.self) {
            SurahLocalDataSourceImpl(surahQueries: resolve(), nameQueries : resolve())
        }
        
        register(AyaLocalDataSource.self) {
            AyaLocalDataSourceImpl(ayaQueries: resolve(), ayaContentQueries : resolve())
        }
        
        // 2-1-1
        register(SettingsDataSource.self) {
            SettingsDataSourceImpl(queries: resolve())
        }
        
        // 2-1-2
        // 2-2-1
        register(CalligraphyLocalDataSource.self) {
            CalligraphyLocalDataSourceImpl(queries: resolve())
        }
    }
    
    public static func registerQueries() {
        
        register(SurahQueries.self) {
            let mainDB : Main = resolve()
            return mainDB.surahQueries
        }
        register(AyaQueries.self) {
            let mainDB : Main = resolve()
            return mainDB.ayaQueries
        }
        register(AyaContentQueries.self) {
            let mainDB : Main = resolve()
            return mainDB.ayaContentQueries
        }
        register(NameQueries.self) {
            let mainDB : Main = resolve()
            return mainDB.nameQueries
        }
       
        // 2-1-1-1
        register(SettingsQueries.self) {
            let mainDB : Main = resolve()
            return mainDB.settingsQueries
        }
       
        // 2-1-2-1
        // 2-2-1-1
        register(CalligraphyQueries.self) {
            let mainDB : Main = resolve()
            return mainDB.calligraphyQueries
        }
    }
    
    public static func registerMappers() {
        
        /*
         Domain MAPPERS - entity to repo
         */
        
        // Mapper<JuzEntity, JuzRepoModel>
        // need HizbEntityRepoMapper
        register(Mapper.self, name: "JuzEntityRepoMapper"){
            JuzDBRepoMapper(hizbMapper : resolve())
        }

        // Mapper<HizbEntity, HizbRepoModel>
        register(Mapper.self, name: "HizbEntityRepoMapper"){
            HizbDBRepoModel()
        }
        
        // Mapper<Aya, AyaRepoModel>
        register(Mapper.self, name: "AyaEntityRepoMapper"){
            AyaEntityRepoMapper()
        }
        
        // Mapper<SurahWithTwoName, SurahRepoModel>
        register(Mapper.self, name: "SurahWithTwoNameEntityRepoMapper"){
            SurahWithTwoNameEntityRepoMapper()
        }
        
        // 2-1-3
        // 2-2-2
        // Mapper<CalligraphyEntity, Calligraphy>
        register(Mapper.self, name: "CalligraphyEntityRepoMapper") {
            CalligraphyEntityRepoMapper()
        }
        
        /*
         UI MAPPERS - repo to ui
         */
        
        // Mapper<JuzRepoModel, JozUIModel>
        register(Mapper.self, name: "JuzRepoUIMapper"){
            JuzRepoUIMapper()
        }
        // Mapper<SurahRepoModel, SurahUIModel>
        register(Mapper.self, name: "SurahRepoUIMapper"){
            SurahRepoUIMapper()
        }
        
        // Mapper<SurahUIModel, SurahItemModel>
//        register(Mapper.self, name: "SurahUIItemMapper"){
//            SurahUIItemMapper()
//        }
        
        // 2-3
        //  Mapper<Calligraphy, CalligraphyUIModel>
        register(Mapper.self, name: "CalligraphyDomainUIMapper"){
            CalligraphyDomainUIMapper()
        }
        
        // Mapper<AyaRepoModel, AyaUIModel>
        register(Mapper.self, name: "AyaRepoUIMapper"){
            AyaRepoUIMapper()
        }
        
        // Mapper<SurahRepoModel, SurahHeaderUIModel>
        register(Mapper.self, name: "SurahRepoHeaderMapper"){
            SurahRepoHeaderMapper()
        }
    }
    
    public static func registerNativeDatabase() {
        
        register(Main.self){
            NativeDatabaseFactory().create()
        }
        .scope(application)
    }
    
    public static func registerNavigator() {
        register(Navigator.self) {
            return iOSNavigator
        }
    }
}
