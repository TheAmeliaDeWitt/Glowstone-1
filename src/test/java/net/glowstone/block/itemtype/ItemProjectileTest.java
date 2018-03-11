package net.glowstone.block.itemtype;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Superclass of tests of {@link ItemProjectile} subclasses.
 *
 * @param <T> the projectile class that we expect will be passed to
 *            {@link Player#launchProjectile(Class)}
 */
public abstract class ItemProjectileTest<T extends Projectile> extends ItemTypeTest
{
	protected final ItemProjectile item;
	protected final Class<T> projectileClass;
	protected final Material type;

	public ItemProjectileTest( ItemProjectile item, Material type, Class<T> projectileClass )
	{
		this.item = item;
		this.type = type;
		this.projectileClass = projectileClass;
	}

	protected void checkProjectile( T projectile )
	{
		verify( player, times( 1 ) ).launchProjectile( projectileClass );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void testUse()
	{
		ItemStack itemStack = new ItemStack( type, 1 );
		inventory.addItem( itemStack );
		Projectile projectile = Mockito.mock( projectileClass );
		when( player.launchProjectile( projectileClass ) ).thenReturn( ( T ) projectile );
		item.use( player, itemStack );
		checkProjectile( ( T ) projectile );
	}
}
