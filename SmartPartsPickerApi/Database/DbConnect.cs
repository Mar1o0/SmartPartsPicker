﻿using LinqToDB;
using LinqToDB.Configuration;
using LinqToDB.Data;
using SmartPartsPickerApi.Database.Providers;
using SmartPartsPickerApi.Database.Tables;
using System.Data.SQLite;

namespace SmartPartsPickerApi.Database
{
    public class DbConnect : ILinqToDBSettings
    {
        public IEnumerable<IDataProviderSettings> DataProviders => Enumerable.Empty<IDataProviderSettings>();

        public string DefaultConfiguration => "SqlServer";
        public string DefaultDataProvider => "SqlServer";

        public IEnumerable<IConnectionStringSettings> ConnectionStrings
        {
            get
            {
                yield return
                    new ConnectionStringSettings
                    {
                        Name = DBData.Name,
                        ProviderName = "System.Data.SQLite",
                        ConnectionString = @"Data Source=" + DBData.GetPathDb()
                    };
            }
        }

        private class ConnectionStringSettings : IConnectionStringSettings
        {
            public string ConnectionString { get; set; }
            public string Name { get; set; }
            public string ProviderName { get; set; }
            public bool IsGlobal => false;
        }
    }
    public class Database
    {
        static Database()
        {
            DataConnection.DefaultSettings = new DbConnect();

            if (!File.Exists(DBData.GetPathDb()))
                SQLiteConnection.CreateFile(DBData.GetPathDb());
        }
        private static async Task<ITable<T>> Create<T>()
        {
            using var db = new DbWinbroker();
            ITable<T> table = null;
            try
            {
                table = db.CreateTable<T>();

            }
            catch (Exception ex)
            { }
            return table;
        }
        public static async Task CreateAllDb()
        {
            UpgradeDB();
            await Create<ProductTable>();
            await Create<ProductImageTable>();
            await Create<ProductPriceTable>();
            await Create<ProductSpecTable>();
            await Create<FilterTable>();
            await Create<ProductFilterTable>();
        }
        private static void UpgradeDB()
        {
            var updates = new[]
            {
                 "",
            };
            foreach (var update in updates)
            {
                using (var db = new DbWinbroker())
                {
                    try
                    {
                        db.Query<ProductTable>(update);
                    }
                    catch (Exception ex)
                    {
                        //ignore because allredy have update
                    }
                }
            }


        }


        public ProductProviders Product { get; } = new ProductProviders();
        public ProductPriceProviders ProductPrice { get; } = new ProductPriceProviders();
        public ProductImageProviders ProductImage { get; } = new ProductImageProviders();
        public ProductSpecProviders ProductSpec { get; } = new ProductSpecProviders();
        public FilterProviders Filter { get; } = new FilterProviders();
        public ProductFilterProvider ProductFilter { get; } = new ProductFilterProvider();
    }
    public class DbWinbroker : DataConnection, IDisposable
    {
        public DbWinbroker() : base(DBData.Name) { }
        #region Tables

        public ITable<ProductTable> Products => GetTable<ProductTable>();
        public ITable<ProductImageTable> ProductImages => GetTable<ProductImageTable>();
        public ITable<ProductPriceTable> ProductPrices => GetTable<ProductPriceTable>();
        public ITable<ProductSpecTable> ProductSpecs => GetTable<ProductSpecTable>();
        public ITable<FilterTable> Filters => GetTable<FilterTable>();
        public ITable<ProductFilterTable> ProductFilters => GetTable<ProductFilterTable>();
        #endregion
    }
    public class DBData
    {
        public const string Name = "Database";
        private const string Database = "Database.db";

        public static string GetPathDb()
        {
            var path = AppDomain.CurrentDomain.BaseDirectory;
            return path + Database;
        }
    }
}
