namespace SmartPartsPickerApi.Enums
{
    public enum SpecType : int
    {
        /// <summary>
        /// Количество ядер
        /// </summary>
        CoreAmount,
        /// <summary>
        /// Количество потоков
        /// </summary>
        ThreadAmount,
        /// <summary>
        /// Базовая тактовая частота
        /// </summary>
        BaseClock,
        /// <summary>
        /// Тактовая частота в режиме Boost
        /// </summary>
        BoostClock,
        /// <summary>
        /// Размер кэша
        /// </summary>
        CacheSize,
        /// <summary>
        /// Тепловыделение
        /// </summary>
        TDP,
        /// <summary>
        /// Объем памяти
        /// </summary>
        MemorySize,
        /// <summary>
        /// Тип памяти
        /// </summary>
        MemoryType,
        /// <summary>
        /// Скорость памяти
        /// </summary>
        MemorySpeed,
        /// <summary>
        /// Версия PCI Express
        /// </summary>
        PCIeVersion,
        /// <summary>
        /// Количество SATA портов
        /// </summary>
        SATAPorts,
        /// <summary>
        /// Количество USB портов
        /// </summary>
        USBPorts,
        /// <summary>
        /// Форм-фактор
        /// </summary>
        FormFactor,
        /// <summary>
        /// Выходная мощность
        /// </summary>
        PowerOutput,
        /// <summary>
        /// Коэффициент полезного действия
        /// </summary>
        EfficiencyRating,
        /// <summary>
        /// Размер вентилятора
        /// </summary>
        FanSize,
        /// <summary>
        /// Скорость вращения вентилятора 
        /// </summary>
        FanRPM,
        /// <summary>
        /// Цвет
        /// </summary>
        Color,
        /// <summary>
        /// Вес
        /// </summary>
        Weight,
        /// <summary>
        /// Габариты
        /// </summary>
        Dimensions,
        /// <summary>
        /// Прочее
        /// </summary>
        ETC
    }
}